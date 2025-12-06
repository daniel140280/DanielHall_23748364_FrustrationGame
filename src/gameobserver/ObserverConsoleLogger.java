package gameobserver;

import helpers.ConsoleColor;
import players.Player;
import playersgamepositions.PlayersInGameContext;
import playersgamepositions.PlayersPosition;

import java.util.Map;

//Class is stateless. Context is passed in via method parameters as the logger shouldn't hold state.
/**
 * SRP: Only responsible for console output formatting and logging.
 * Does NOT make game logic decisions.
 */
public class ObserverConsoleLogger implements GameListener{

    PlayersInGameContext playersPosition;
    /**
     * Method to track when a player rolls the dice and attempts to move.
     * It will store the roll and updates the move positions.
     */
    @Override
    public void onSuccessfulMove(Player player, PlayersInGameContext context, int fromPosition, int toPosition, int roll) {
        String message = String.format(
                "%s rolled %d with the dice | moving from %d to %d | successful move | total moves: %d",
                player.getName(), roll, fromPosition, toPosition, context.getMoveCount()
        );
        // Wrap the entire message in the player's color
        System.out.println(ConsoleColor.consoleColor(message, player.getColorCode()));
        // record successful move in player move history
        context.getPlayersHistory().add("Successful roll, moved to " + context.getPlayersPosition().toString());
    }
    /**
     * Method to output a 'hit' if a players move is blocked by another player.
     */
    @Override
    public void onBlockedMove(Player player, PlayersInGameContext context, int attemptedPosition, int roll) {
        String message = String.format(
                "%s rolled %d with the dice | move forfeited, hit another player at position %d | stays on position %s | total moves: %d",
                player.getName(), roll, attemptedPosition, context.getPlayersPosition().toString(), context.getMoveCount()
        );
        System.out.println(ConsoleColor.consoleColor(message, player.getColorCode()));
        //Update player history
        context.getPlayersHistory().add("Move forfeited (hit), stays on " + context.getPlayersPosition().toString());
    }
    /**
     * Method to output when a player reaches or overshoots the end of the board.
     */
    /**
     * FIXED: Method to output when a player reaches or overshoots the end of the board.
     * WHY CHANGED: Removed dependency on isAtEnd() - determine win from overshoot value instead
     * Logic:
     * - overshoot == 0: Exact landing on end (WIN)
     * - overshoot > 0 with move applied: OvershootAllowed strategy (WIN)
     * - overshoot > 0 with move NOT applied: ExactEnd strategy (FORFEIT)
     */
    /**
     * Method to output when a player successfully reaches the end (WIN).
     */
    @Override
    public void onEndReached(Player player, PlayersInGameContext context, int attemptedPosition, int overshoot, int roll) {
        String message;
        // CHANGED: Determine win condition from overshoot and context
        // If overshoot == 0, it's an exact landing (WIN for both strategies)
        if(overshoot == 0) {
            message = String.format(
                    "%s rolled %d with the dice | landed exactly on the end at %s, so we have a winner | total moves: %d",
                    player.getName(), roll, context.getPlayersPosition().toString(), context.getMoveCount()
            );
            //Update player history
            context.getPlayersHistory().add("🎉 Reached end at " + context.getPlayersPosition().toString());
        } else {
            // overshoot > 0
            // CHANGED: Check if in tail to determine if win or forfeit
            // If in tail, OvershootAllowedStrategy applied the move (WIN)
            // If not in tail, ExactEndStrategy forfeited the move (FORFEIT)
            if (context.getPlayersPosition().isInTail()) {
                // Move was applied - OvershootAllowedStrategy (WIN)
                message = String.format(
                        "%s rolled %d with the dice | overshot by %d but allowed, winner at %s! | total moves: %d",
                        player.getName(), roll, overshoot, context.getPlayersPosition().toString(), context.getMoveCount()
                );
                context.getPlayersHistory().add("🎉 Reached end (overshoot allowed) at " + context.getPlayersPosition().toString());

            } else {
                // Move was NOT applied - ExactEndStrategy forfeited (FORFEIT)
                message = String.format(
                        "%s rolled %d with the dice | overshot by %d, move forfeited, stays on %s | total moves: %d",
                        player.getName(), roll, overshoot, context.getPlayersPosition().toString(), context.getMoveCount()
                );
                context.getPlayersHistory().add("Overshoot. Move forfeited, stays on " + context.getPlayersPosition().toString());
            }
        }
            System.out.println(ConsoleColor.consoleColor(message, player.getColorCode()));
        }
//            if (overshoot > 0 && !context.getPlayersPosition().isAtEnd()) {
//                //Overshoot, forfeited move
//                message = String.format(
//                        "%s rolled %d with the dice | Overshoot. Move forfeited, stay on %s | total moves: %d",
//                        player.getName(), roll, context.getPlayersPosition().toString(), context.getMoveCount()
//                );
//                context.getPlayersHistory().add("Overshoot. Move forfeited, stays on " + context.getPlayersPosition().toString());
//            } else if (context.getPlayersPosition().isAtEnd() && overshoot > 0) {
//                // OvershootAllowedStrategy winner
//                message = String.format(
//                        "%s rolled %d | overshot but allowed, winner at %s | total moves: %d",
//                        player.getName(), roll, context.getPlayersPosition().toString(), context.getMoveCount()
//                );
//                context.getPlayersHistory().add("Reached end at " + context.getPlayersPosition().toString());
//            } else {
//                // Fallback
//                message = String.format(
//                        "%s rolled %d | end condition triggered at %s | total moves: %d",
//                        player.getName(), roll, context.getPlayersPosition().toString(), context.getMoveCount()
//                );
//            }
//        }
//
//        //Update player history
//            context.getPlayersHistory().add("Overshoot. Move forfeited, stays on " + context.getPlayersPosition().toString());
//        }
    /**
     * REQUIRED CHANGE: Method to output when a player overshoots with a strategy that forbids it (FORFEIT).
     */
    @Override
    public void onEndForfeit(Player player, PlayersInGameContext context, int attemptedPosition, int overshoot, int roll) {
        String message = String.format(
                "%s rolled %d with the dice | overshot by %d, move forfeited, stays on %s | total moves: %d",
                player.getName(), roll, overshoot, context.getPlayersPosition().toString(), context.getMoveCount()
        );
        System.out.println(ConsoleColor.consoleColor(message, player.getColorCode()));
        context.getPlayersHistory().add("Overshoot. Move forfeited, stays on " + context.getPlayersPosition().toString());
    }

    @Override
    public void onGameOver(Player[] players, Map<Player, PlayersInGameContext> contexts) {
        System.out.println("\nEnd of game status:");
        for (Player player : players) {
            PlayersInGameContext context = contexts.get(player);
            System.out.println("\n" + player.getColorCode() + player.getName() + "\u001B[0m");
            System.out.printf("Moves made: %d  | ", context.getMoveCount());
            System.out.printf("Final position: %s | ", context.getPlayersPosition());
            System.out.printf("Move history: %s", context.getPlayersHistory().getAllMoves());
        }
    }
}
