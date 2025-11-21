package factories.adapters;

import board.LargeGameBoard;
import factories.BoardFactory;
import board.GameBoard;
import gameconfig.BoardOption;

public class LargeGameBoardFactoryAdapter implements BoardFactory {
    @Override
    public GameBoard createBoard(BoardOption option) {
        return new LargeGameBoard();
    }
}
