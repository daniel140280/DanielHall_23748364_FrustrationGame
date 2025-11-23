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
     *  * - Players now move clockwise around the shared board using modular arithmetic.
     *  * - Tail entry only after completing a full lap (stepsTaken >= boardLength).
     *  * - Overshoot only applies when reaching tail end.
     *  * - Strategy decides outcome, listeners handle console output + history.
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
        int fromIndex = context.getPlayersPosition().getBoardIndex();
        int stepsSoFar = context.getStepsTaken();
        int totalSteps = stepsSoFar + roll;
        int proposedIndex;
        int sharedBoardLength = board.getBoardLength();                 //18 or 36 positions depending on SMALL or LARGE gameboard.

        //Determine if player enters tail
        if(totalSteps < sharedBoardLength){
            //Still on shared board, wrap around
            proposedIndex = (fromIndex + roll) % sharedBoardLength;
            context.getPlayersPosition().setInTail(false);
        } else {
            //enter the tail - calculate the tail index
            int tailOffset = totalSteps - sharedBoardLength;
            proposedIndex = sharedBoardLength + tailOffset;
            context.getPlayersPosition().setInTail(true);
        }
        // Hit Strategy check - Check if move is allowed
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
//        context.getPlayersHistory().add("Moved to " + proposedIndex);

        // End Strategy check - Did they reach the end?
        int tailEndIndex = board.getBoardLength() + board.getTailEndLength() -1;
        if (endStrategy.hasReachedEnd(player, proposedIndex) && proposedIndex == tailEndIndex) {
            int overshoot = endStrategy.calculateOvershoot(player, proposedIndex);
            for (GameListener listener : listeners) {
                listener.onEndReached(player, context, proposedIndex, overshoot, roll);
            }
//            context.getPlayersHistory().add("🎉 Reached end");
        } else {
            for (GameListener listener : listeners) {
                listener.onSuccessfulMove(player, context, fromIndex, proposedIndex, roll);
            }
        }
    }
}
