package board;

public interface GameBoard {
    int getBoardLength();               //Total number of playable positions (shared between players, excl the end tail).
    int getTailEndLength();             //Number of positions in the tail (end).
    int getTotalBoardLength();          //Combined Board and Tail End length.
    boolean isEndPosition(int index);   //Is this index within the end tail positions.
    boolean isValidPosition(int index); //Is this index within the bounds of the board?
}
