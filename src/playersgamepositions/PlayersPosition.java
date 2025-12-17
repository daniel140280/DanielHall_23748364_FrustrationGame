package playersgamepositions;

import board.GameBoard;
import players.Player;

import java.util.Objects;

/*
Value Object used to track the position of the players as they traverse the board.
Strategies will determine whether board positions are valid or forfeit.
History will be kept to track game statistics.
Observer will listen to position progress and print relevant information to the console.
 */
/**
 * SRP: Tracks runtime position only.
 * Tail flag is set by strategy, not by Player or Board.
 * Tail metadata (offset, end flag) is set by StandardMoveStrategy.
 *  * SRP: Tracks position and handles display formatting.
 *  * Does NOT handle game logic or strategy decisions.
 */

public class PlayersPosition {
    private int boardIndex;                             // Tracking current board index based on the player in context.
    private boolean indexInTail;                        // Supporting whether player is in their tail position.
//    MORNING private int tailOffset;                             // Will be '0' when not in tail, otherwise '1' onwards.
//    MORNING private boolean atEnd;                              // True when at last tail cell, render as 'End'.
    private final Player player;
    private final int boardLength;      // ✅ ADDED: Store board length
    private final int tailLength;       // ✅ ADDED: Store tail length

    public PlayersPosition(Player player, GameBoard board){
        this.player = player;
        this.boardIndex = player.getStartIndex();       //When a player is created, we automatically assign their starting board index position.
        this.indexInTail = false;
        this.boardLength = board.getBoardLength();    // ✅ ADDED: Cache board length
        this.tailLength = board.getTailEndLength();   // ✅ ADDED: Cache tail length
//        MORNING this.tailOffset = 0;
//        MORNING this.atEnd = false;
    }

    //Methods to manage the Players game moves depending on game strategies applied.
    public int getBoardIndex() {
        return boardIndex;
    }
    public void setBoardIndex(int boardIndex) {
        this.boardIndex = boardIndex;
    }
    public boolean isInTail() {
        return indexInTail;
    }
    public void setInTail(boolean inTail) {
        this.indexInTail = inTail;
    }
//    public int getTailOffset() { return tailOffset; }
//    public void setTailOffset(int tailOffset) { this.tailOffset = tailOffset; }
//    public boolean isAtEnd() { return atEnd; }
//    public void setAtEnd(boolean atEnd) { this.atEnd = atEnd; }
    public Player getPlayer() {
        return player;
    }


    /**
     * FIXED toString() - Correct tail formatting.
     *
     * Examples:
     * - Shared board: "5", "12", "0"
     * - Small board tail: "R1", "R2", "REnd" (positions 18, 19, 20)
     * - Large board tail: "R1", "R2", "R3", "R4", "R5", "REnd" (positions 36-41)
     *
     * WHY: Calculates tail offset dynamically from boardIndex
     * CHANGE: Uses boardIndex to determine tail position, not separate tailOffset field
     */
    @Override
    public String toString() {
        if (!indexInTail) {
            if(boardIndex == player.getStartIndex()){
                return "Home (position " + (boardIndex +1) + ")";
            }
            return "position " + (boardIndex + 1);
//            return String.valueOf(boardIndex);
        }
        // In tail - format as "R1", "R2", "REnd"
        String initial = player.getName().substring(0, 1);
        int tailPosition = boardIndex - boardLength + 1;  // FIXED: Correct calculation

        // Check if at final tail position
//        int finalTailPosition = tailLength;

        if (tailPosition >= tailLength) {
            return initial + "End";
        }
        return "Tail position " + (initial + tailPosition);
    }
}
//        MORNING String initial = player.getName().substring(0,1);
//        return atEnd ? initial + "End" : initial + tailOffset;
//    }
// Calculate tail position (1-indexed for display)
// boardIndex 18 (small) = tail pos 1, boardIndex 19 = tail pos 2
// boardIndex 36 (large) = tail pos 1, boardIndex 37 = tail pos 2
