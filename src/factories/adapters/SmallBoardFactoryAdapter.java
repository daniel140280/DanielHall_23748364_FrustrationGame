package factories.adapters;

import board.BoardEnums;
import board.GameBoard;
import board.SmallGameBoard;
import gameconfig.BoardOption;
import factories.BoardFactory;

public class SmallBoardFactoryAdapter implements BoardFactory {
    @Override
    public GameBoard createBoard(BoardOption option) {
        return new SmallGameBoard();
    }

}
