package players;

import board.GameBoard;

/**
 * Tail indices are calculated using GameBoard abstraction.
 * Player only knows its start index; everything else comes from the board abstraction.
 */

public class BluePlayer implements Player {
    private final String name = "Blue";
    private final int startIndex = 9;               //Always starts at same index regardless of board size.
//    private final int tailStartIndex = 10;
//    private final int tailEndIndex = 12;
    private final String colorCode = "\u001B[34m";  //Blue colour.

    public BluePlayer(){

    }

    @Override
    public String getName() {
        return name;
    }
    @Override
    public int getStartIndex() {
        return startIndex;
    }
//    @Override
//    public int getTailStartIndex() {
//        //Tail starts immediately after traversing the board length.
//        return board.getBoardLength();
////        return tailStartIndex;
//    }
//
//    @Override
//    public int getTailEndIndex() {
//        // Tail end = tail start + tail length - 1
//        return getTailStartIndex() + board.getTailEndLength() -1;
////        return tailEndIndex;
//    }
    @Override
    public String getColorCode() {
        return colorCode;
    }
}
