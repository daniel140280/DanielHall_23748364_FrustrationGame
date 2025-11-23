package players;

import board.GameBoard;
import playersgamepositions.PlayersInGameContext;

/**
 * SRP: Player only knows identity and start index.
 * No tail logic here.
 */

public class RedPlayer implements Player{
    private final String name = "Red";
    private final int startIndex = 0;               //Always starts at same index regardless of board size.
//    private final int tailStartIndex = 18;
//    private final int tailEndIndex = 20;
    private final String colorCode = "\u001B[31m";  //Red colour.

    //Holding a Players state and behaviour
    //private final PlayersInGameContext playerContext;

    public RedPlayer(){

    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public int getStartIndex() {
        return startIndex;
    }
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
