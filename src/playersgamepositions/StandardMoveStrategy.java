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
     *  * Responsibilities:
     *  * 1. Calculate proposed position
     *  * 2. Validate move using strategies
     *  * 3. Apply move or forfeit
     *  * 4. Notify listeners
     *  *
     *  * Does NOT handle formatting or display logic.
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

        //1. Calculating the proposed position (hypothetical state depending on strategy applied)
        int totalSteps = stepsSoFar + roll;                             //calculates hypothetical new total distance.
        int proposedIndex;
        boolean proposedInTail;

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
        }
        //2. Validate move using End Strategy - determines if overshoot allowed (strategy dependent) before applying move with correct parameters
        if(!endStrategy.isValidMove(player,fromIndex,roll,sharedBoardLength,tailLength,stepsSoFar)){
            //Move overshoots - forfeit (ExactEndStrategy only)
            context.increaseMoveCount();

            int overshoot = endStrategy.calculateOvershoot(player, proposedIndex);

            for (GameListener listener : listeners) {
                listener.onEndForfeit(player, context, proposedIndex, overshoot, roll);
            }
            return; //Overshoot forfeit, don't apply move
        }

//          MORNING  if (proposedInTail) {
//                context.getPlayersPosition().setTailOffset(tailLength);
//                context.getPlayersPosition().setAtEnd(true);
//            }
//
//            for(GameListener listener : listeners){
//                listener.onEndReached(player, context, proposedIndex, overshoot, roll);
//            }
//            return; //Stop, do not apply move or check collisions.

        //3. Validate move using Hit Strategy - check if move is allowed (only on the shared board)
        if (!proposedInTail && !hitStrategy.canMoveToPosition(player, proposedIndex, allPlayers, this.board)) {
            //Hit another player, forfeit go.
            context.increaseMoveCount();
            for (GameListener listener : listeners) {
                listener.onBlockedMove(player, context, proposedIndex, roll);
            }
            return; //Stop, collision detected.
        }

        //4. Apply valid move and update the player position state.
        context.getPlayersPosition().setBoardIndex(proposedIndex);
        context.getPlayersPosition().setInTail(proposedInTail);
        context.advanceStepsTaken(roll);
        context.increaseMoveCount();

//        MORNINGif (proposedInTail) {
//            int appliedTailOffset = Math.min(totalSteps - sharedBoardLength, tailLength);
//            context.getPlayersPosition().setTailOffset(appliedTailOffset);
//            boolean atEnd = proposedIndex == tailEndIndex;
//            context.getPlayersPosition().setAtEnd(atEnd);
//        } else {
//            context.getPlayersPosition().setTailOffset(0);
//            context.getPlayersPosition().setAtEnd(false);
//        }


//        context.getPlayersHistory().add("Moved to " + proposedIndex);

        //5. Validate End Strategy win condition - did a player reach the end?
//        int tailEndIndex = board.getBoardLength() + board.getTailEndLength() -1;
        if (endStrategy.hasReachedEnd(player, proposedIndex)) {
            //Player has WON!
//            if (proposedIndex > tailEndIndex) {
//                context.getPlayersPosition().setBoardIndex(tailEndIndex);
//            }
//            int overshoot = endStrategy.calculateOvershoot(player, proposedIndex);
//            for (GameListener listener : listeners) {
//                listener.onEndReached(player, context, proposedIndex, overshoot, roll);
//            }
            // >>> REQUIRED CHANGE START
            // Player has WON!

            // Ensure the position is capped at the actual tail end index for display purposes,
            // even if the proposedIndex calculation was higher (due to overshoot).
            // This caps the visual position to "REnd".
            int finalIndexForWin = Math.min(proposedIndex, tailEndIndex);
            context.getPlayersPosition().setBoardIndex(finalIndexForWin);
            context.getPlayersPosition().setInTail(true); // Must be in tail to win

            int overshoot = endStrategy.calculateOvershoot(player, proposedIndex);
            for (GameListener listener : listeners) {
                // Notifies that the player won (either exact land or allowed overshoot)
                listener.onEndReached(player, context, finalIndexForWin, overshoot, roll);
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

