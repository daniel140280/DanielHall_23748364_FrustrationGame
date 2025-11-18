package gameobserver;

import helpers.ConsoleColor;
import players.Player;
import playersgamepositions.PlayersInGameContext;
import playersgamepositions.PlayersPosition;

public class ObserverConsoleLogger implements GameListener{

    PlayersInGameContext playersPosition;
    /**
     * Method to track when a player rolls the dice and attempts to move.
     * It will store the roll and updates the move positions.
     */
    @Override
    public void onSuccessfulMove(Player player, int fromPosition, int toPosition, int roll){
        String message = String.format(
                "%s rolled %d with the dice | moving from %d to %d | successful move | total moves: %d",
                player.getName(), roll, fromPosition, toPosition, playersPosition.getMoveCount()
        );
        // Wrap the entire message in the player's color
        System.out.println(ConsoleColor.consoleColor(message, player.getColorCode()));
    }
    /**
     * Method to output a 'hit' if a players move is blocked by another player.
     */
    @Override
    public void onBlockedMove(Player player, int attemptedPosition, int roll){
        String message = String.format(
                "%s rolled %d with the dice | and is blocked at position %d | move forfeited | total moves: %d",
                player.getName(), roll, attemptedPosition, playersPosition.getMoveCount()
        );
        // Wrap the entire message in the player's color
        System.out.println(ConsoleColor.consoleColor(message, player.getColorCode()));
    }
    /**
     * Method to output when a player reaches or overshoots the end of the board.
     */
    @Override
    public void onEndReached(Player player, int attemptedPosition, int overshoot){
        String comment = overshoot == 0 ? "Landed exactly on the end" : "Made the end, but did overshoot by " + overshoot + " moves.";

        String message = String.format(
                "%s rolled %d with the dice | attempted to land on %d | %s | total moves: %d",
                player.getName(), attemptedPosition - playersPosition.getMoveCount(), attemptedPosition, comment, playersPosition.getMoveCount()
        );
        // Wrap the entire message in the player's color
        System.out.println(ConsoleColor.consoleColor(message, player.getColorCode()));
    }

    @Override
    public void onGameOver(Player[] players){
        System.out.println("\nEnd of game status:");
        for(Player player: players){
            System.out.println("\n" + player.getColorCode() + player.getName() + "\u001B[0m");
            System.out.println("Moves made: " + playersPosition.getMoveCount());
            System.out.println("Final position: " + playersPosition.getPlayersPosition());
            System.out.println("Move history: " + playersPosition.getPlayersHistory());
        }
    }
}
