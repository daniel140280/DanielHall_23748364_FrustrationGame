package boardfactory;

import board.BoardEnums;
import board.GameBoard;
import board.LargeGameBoard;
import board.SmallGameBoard;

public class GameBoardFactory {
    /*
    Creating a simple factory to allow us to access and instantiating the required gameboard at runtime.
     */
    public static GameBoard createBoard(BoardEnums size){
        return switch (size){
            case SMALL -> new SmallGameBoard();
            case LARGE -> new LargeGameBoard();
        };
    }
}
