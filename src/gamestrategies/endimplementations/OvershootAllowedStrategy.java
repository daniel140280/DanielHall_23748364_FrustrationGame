package gamestrategies.endimplementations;

import board.GameBoard;
import gamestrategies.EndStrategy;
import players.Player;

/**
 * OvershootAllowedStrategy lets players finish even if they roll past their tail end.
 * End logic now uses GameBoard lengths, not Player indices.
 * Players can finish even if they roll past their tail end.
 */

public class OvershootAllowedStrategy implements EndStrategy {
    private final GameBoard board;                      //injected board - perfect for beans?!?

    public OvershootAllowedStrategy(GameBoard board) {  // require board
        this.board = board;
    }

    @Override
    public boolean hasReachedEnd(Player player, int currentIndex) {
        int tailEndIndex = board.getBoardLength() + board.getTailEndLength() - 1;
        return currentIndex >= tailEndIndex;
    }

    @Override
    public int calculateOvershoot(Player player, int currentIndex) {
        int tailEndIndex = board.getBoardLength() + board.getTailEndLength() - 1;
        return Math.max(0, currentIndex - tailEndIndex);
    }
}
