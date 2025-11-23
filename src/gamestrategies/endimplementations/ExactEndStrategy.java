package gamestrategies.endimplementations;

import board.GameBoard;
import gamestrategies.EndStrategy;
import players.Player;

/**
 * ExactEndStrategy requires players to land exactly on their tail end.
 * End logic uses GameBoard lengths, not Player indices.
 * Players must land exactly on their tail end.
 */

public class ExactEndStrategy implements EndStrategy {
    private final GameBoard board;              //injected board - perfect for beans?!?

    public ExactEndStrategy(GameBoard board) { // require board
        this.board = board;
    }

    @Override
    public boolean hasReachedEnd(Player player, int currentIndex) {
        int tailEndIndex = board.getBoardLength() + board.getTailEndLength() -1;
        return currentIndex == tailEndIndex;
    }

    @Override
    public int calculateOvershoot(Player player, int currentIndex) {
        int tailEndIndex = board.getBoardLength() + board.getTailEndLength() -1;
        return currentIndex > tailEndIndex ? currentIndex - tailEndIndex : 0;
    }
}
