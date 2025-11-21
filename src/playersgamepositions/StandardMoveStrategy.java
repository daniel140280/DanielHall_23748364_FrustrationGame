package playersgamepositions;

import board.GameBoard;
import gameobserver.GameListener;
import gamestrategies.EndStrategy;
import gamestrategies.HitStrategy;
import players.Player;

import java.util.List;
import java.util.Map;

public class StandardMoveStrategy implements MoveStrategy {
    private final GameBoard board;
    private final HitStrategy hitStrategy;
    private final EndStrategy endStrategy;
    private final List<GameListener> listeners;
    private final Map<Player, PlayersInGameContext> allPlayers;

    /**
     * StandardMoveStrategy handles player movement around the shared board and into their own tail.
     * - Explicit modular wrap-around for shared board.
     * - Tail entry only after completing full lap (boardLength).
     * - EndStrategy calls with Player parameter.
     */

    public StandardMoveStrategy(GameBoard board, HitStrategy hitStrategy, EndStrategy endStrategy,
                                List<GameListener> listeners, Map<Player, PlayersInGameContext> allPlayers) {
        this.board = board;
        this.hitStrategy = hitStrategy;
        this.endStrategy = endStrategy;
        this.listeners = listeners;
        this.allPlayers = allPlayers;
    }

    @Override
    public void move(PlayersInGameContext context, int roll) {
        Player player = context.getPlayersPosition().getPlayer();
        int currentIndex = context.getPlayersPosition().getBoardIndex();
        int totalSteps = context.getStepsTaken() + roll;
        int proposedIndex;

        //Determine if player enters tail
        if(totalSteps < board.getBoardLength()){
            //Still on shared board, wrap around
            proposedIndex = (currentIndex + roll) % board.getBoardLength();
        } else {
            //enter the tail - calculate the tail index
            int tailOffset = totalSteps - board.getBoardLength();
            proposedIndex = player.getTailStartIndex() + tailOffset;
            context.getPlayersPosition().setInTail(true);
        }
        // Check if move is allowed
        if (!hitStrategy.canMoveToPosition(player, proposedIndex, allPlayers)) {
            context.increaseMoveCount();
            for (GameListener listener : listeners) {
                listener.onBlockedMove(player, context, proposedIndex, roll);
            }
            return;
        }

        // Apply move
        context.getPlayersPosition().setBoardIndex(proposedIndex);
        context.advanceStepsTaken(roll);
        context.increaseMoveCount();
        context.getPlayersHistory().add("Moved to " + proposedIndex);

        // Notify listeners
        if (endStrategy.hasReachedEnd(player, proposedIndex)) {
            int overshoot = endStrategy.calculateOvershoot(player, proposedIndex);
            for (GameListener listener : listeners) {
                listener.onEndReached(player, context, proposedIndex, overshoot);
            }
            context.getPlayersHistory().add("🎉 Reached end");
        } else {
            for (GameListener listener : listeners) {
                listener.onSuccessfulMove(player, context, currentIndex, proposedIndex, roll);
            }
        }
    }
}
