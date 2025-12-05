package gamestrategies.endimplementations;

import board.GameBoard;
import gamestrategies.EndStrategy;
import players.Player;

/**
 * OvershootAllowedStrategy lets players finish even if they roll past their tail end.
 * End logic now uses GameBoard lengths, not Player indices.
 * Players can finish even if they roll past their tail end.
 */

/**
 * OvershootAllowedStrategy lets players finish even if they roll past their tail end.
 *
 * FIXED: isValidMove() always returns true since overshoot is allowed.
 */
public class OvershootAllowedStrategy implements EndStrategy {
    private final GameBoard board;

    public OvershootAllowedStrategy(GameBoard board) {
        this.board = board;
    }

    @Override
    public boolean hasReachedEnd(Player player, int currentIndex) {
        int tailEndIndex = board.getBoardLength() + board.getTailEndLength() - 1;
        // CORRECT: >= allows overshoot
        return currentIndex >= tailEndIndex;
    }

    @Override
    public int calculateOvershoot(Player player, int currentIndex) {
        int tailEndIndex = board.getBoardLength() + board.getTailEndLength() - 1;
        // CORRECT: Calculate actual overshoot
        return Math.max(0, currentIndex - tailEndIndex);
    }

    @Override
    public boolean isValidMove(Player player, int currentIndex, int roll, int boardLength, int tailLength, int stepsTaken) {
        // CORRECT: Always valid since overshoot is ALLOWED
        return true;
    }
}