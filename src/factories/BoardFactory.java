package factories;

import board.GameBoard;
import gameconfig.BoardOption;

/*
Board factory abstraction utilises the configuration enums to determine the concrete instantiation.
 */
public interface BoardFactory {
    GameBoard createBoard(BoardOption option);          // SMALL or LARGE board

}
