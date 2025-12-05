package gamestrategies.endimplementations;

import board.GameBoard;
import gamestrategies.EndStrategy;
import players.Player;

/**
 * ExactEndStrategy requires players to land exactly on their tail end.
 * End logic uses GameBoard lengths, not Player indices.
 * Players must land exactly on their tail end.
 */

//public class ExactEndStrategy implements EndStrategy {
//    private final GameBoard board;              //injected board - perfect for beans?!?
//
//    public ExactEndStrategy(GameBoard board) { // require board
//        this.board = board;
//    }
//
//    @Override
//    public boolean hasReachedEnd(Player player, int currentIndex) {
//        int tailEndIndex = board.getBoardLength() + board.getTailEndLength() -1;
//        return currentIndex == tailEndIndex;
//    }
//
//    @Override
//    public int calculateOvershoot(Player player, int currentIndex) {
//        return 0; //Cannot overshoot in exact end strategy. Valid move will prevent this.
//    }
////    public int calculateOvershoot(Player player, int currentIndex) {
////        int tailEndIndex = board.getBoardLength() + board.getTailEndLength() -1;
////        return currentIndex > tailEndIndex ? currentIndex - tailEndIndex : 0;
////    }
//
//    @Override
//    public boolean isValidMove(Player player, int currentIndex, int roll, int boardLength, int tailLength, int stepsTaken) {
////        int tailEndIndex = boardLength + tailLength - 1;
////        return (currentIndex + roll) <= tailEndIndex;
//
//        // Calculate the target absolute distance (linear)
//        int proposedTotalSteps = stepsTaken + roll;
//
//        // Calculate the absolute distance of the final hole
//        // (Board Length wraps) + (Tail Length)
//        // If board is 18 and tail is 3, the final hole is effectively at step 20 (indexes 0-17 are board, 18,19,20 are tail).
//        int finalStepIndex = boardLength + tailLength;
//
//        // Actually, based on your logic:
//        // Index 0..17 (18 squares).
//        // Tail Index 18, 19, 20. (3 squares).
//        // So target index is boardLength + tailLength - 1.
//        int maxIndex = boardLength + tailLength - 1;
//
//        // If total steps (which maps to index in tail) > maxIndex, move is invalid.
//        return proposedTotalSteps <= maxIndex; // or however your stepsTaken maps to index
//    }
//}
/**
 * ExactEndStrategy requires players to land exactly on their tail end.
 */
public class ExactEndStrategy implements EndStrategy {
    private final GameBoard board;

    public ExactEndStrategy(GameBoard board) {
        this.board = board;
    }

    @Override
    public boolean hasReachedEnd(Player player, int currentIndex) {
        int tailEndIndex = board.getBoardLength() + board.getTailEndLength() - 1;
        return currentIndex == tailEndIndex;
    }

    @Override
    public int calculateOvershoot(Player player, int currentIndex) {
        int tailEndIndex = board.getBoardLength() + board.getTailEndLength() - 1;
        return Math.max(0, currentIndex - tailEndIndex); // Cannot overshoot in exact end strategy. Return actual overshoot amount, not 0.
    }

    @Override
    public boolean isValidMove(Player player, int currentIndex, int roll, int boardLength, int tailLength, int stepsTaken) {
        // Check proposed position would overshoot.
        int proposedTotalSteps = stepsTaken + roll;
        int tailEndIndex = boardLength + tailLength - 1;

        // WHY: If proposedTotalSteps > tailEndIndex, move overshoots = INVALID for exact strategy
        // If proposedTotalSteps <= tailEndIndex, move is valid (including exact landing)
        return proposedTotalSteps <= tailEndIndex;

//        int maxTotalSteps = boardLength + tailLength - 1;
//        int proposedTotalSteps = stepsTaken + roll;
//        return proposedTotalSteps <= maxTotalSteps;
    }
}
