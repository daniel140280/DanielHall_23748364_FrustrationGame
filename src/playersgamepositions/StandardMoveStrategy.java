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
     * Players move clockwise around a shared board using modular arithmetic.
     * Tail entry only happens AFTER completing full lap (boardLength).
     * Overshoot only applies when reaching tail end.
     * Strategy decides outcome, listeners handle console output + history.
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
        int sharedBoardLength = board.getBoardLength();                 //18 or 36 positions depending on SMALL or LARGE gameboard.
        int tailLength = board.getTailEndLength();
        int tailEndIndex = sharedBoardLength + tailLength - 1;

        //Calculating the proposed position
        int totalSteps = stepsSoFar + roll;                             //calculates hypothetical new total distance.
        int proposedIndex;
        boolean proposedInTail;

        //1. Calculate proposed position (hypothetical state depending on strategy applied)
        if(totalSteps < sharedBoardLength){
            //Still on shared board, wrap around
            proposedIndex = (fromIndex + roll) % sharedBoardLength;
            proposedInTail = false;
            //context.getPlayersPosition().setInTail(false);
        } else {
            //In tail or entering the tail - calculate the tail index as linear section. No wrap around.
            int tailOffset = totalSteps - sharedBoardLength;
            proposedIndex = sharedBoardLength + tailOffset;
            proposedInTail = true;
//            context.getPlayersPosition().setInTail(proposedInTail);
//            context.getPlayersPosition().setInTail(true);
        }
        //2. End Strategy check - check if move would overshoot (strategy dependent) before applying move with correct parameters
        if(!endStrategy.isValidMove(player,fromIndex,roll,sharedBoardLength,tailLength,stepsSoFar)){
            //Move overshoots - forfeit
            context.increaseMoveCount();

            int overshoot = endStrategy.calculateOvershoot(player, proposedIndex);
            if (proposedInTail) {
                context.getPlayersPosition().setTailOffset(tailLength);
                context.getPlayersPosition().setAtEnd(true);
            }

            for(GameListener listener : listeners){
                listener.onEndReached(player, context, proposedIndex, overshoot, roll);
            }
            return; //Stop, do not apply move or check collisions.
        }
        //3. Hit Strategy check - Check if move is allowed
        if (!proposedInTail && !hitStrategy.canMoveToPosition(player, proposedIndex, allPlayers, this.board)) {
            //Hit another player, forfeit go.
            context.increaseMoveCount();
            for (GameListener listener : listeners) {
                listener.onBlockedMove(player, context, proposedIndex, roll);
            }
            return; //Stop, collision detected.
        }

        //4. Apply valid move and update the player state.
        context.getPlayersPosition().setBoardIndex(proposedIndex);
        context.getPlayersPosition().setInTail(proposedInTail);

        if (proposedInTail) {
            int appliedTailOffset = Math.min(totalSteps - sharedBoardLength, tailLength);
            context.getPlayersPosition().setTailOffset(appliedTailOffset);
            boolean atEnd = proposedIndex == tailEndIndex;
            context.getPlayersPosition().setAtEnd(atEnd);
        } else {
            context.getPlayersPosition().setTailOffset(0);
            context.getPlayersPosition().setAtEnd(false);
        }

        context.advanceStepsTaken(roll);
        context.increaseMoveCount();
//        context.getPlayersHistory().add("Moved to " + proposedIndex);

        //5. End Strategy check - Did they reach the end?
//        int tailEndIndex = board.getBoardLength() + board.getTailEndLength() -1;
        if (endStrategy.hasReachedEnd(player, proposedIndex)) {
            //Player has WON!
            int overshoot = endStrategy.calculateOvershoot(player, proposedIndex);
            for (GameListener listener : listeners) {
                listener.onEndReached(player, context, proposedIndex, overshoot, roll);
            }
//            context.getPlayersHistory().add("🎉 Reached end");
        } else {
            //Normal game move - not won yet!
            for (GameListener listener : listeners) {
                listener.onSuccessfulMove(player, context, fromIndex, proposedIndex, roll);
            }
        }
    }
}

