package gamestrategies.endimplementations;

import board.GameBoard;
import gamestrategies.EndStrategy;
import players.Player;

/**
 * ExactEndStrategy requires players to land exactly on their tail end.
 * End logic uses GameBoard lengths, not Player indices.
 * Players must land exactly on their tail end.
 */
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

    }
}
