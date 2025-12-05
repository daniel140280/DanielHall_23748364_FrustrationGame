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
 */

public class PlayersPosition {
    private int boardIndex;                             // Tracking current board index based on the player in context.
    private boolean indexInTail;                        // Supporting whether player is in their tail position.
    private int tailOffset;                             // Will be '0' when not in tail, otherwise '1' onwards.
    private boolean atEnd;                              // True when at last tail cell, render as 'End'.
    private final Player player;

    public PlayersPosition(Player player){
        this.player = player;
        this.boardIndex = player.getStartIndex();       //When a player is created, we automatically assign their starting board index position.
        this.indexInTail = false;
        this.tailOffset = 0;
        this.atEnd = false;
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
    public int getTailOffset() { return tailOffset; }
    public void setTailOffset(int tailOffset) { this.tailOffset = tailOffset; }
    public boolean isAtEnd() { return atEnd; }
    public void setAtEnd(boolean atEnd) { this.atEnd = atEnd; }
    public Player getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        if(!indexInTail){
            return String.valueOf(boardIndex);
        }
        String initial = player.getName().substring(0,1);
        return atEnd ? initial + "End" : initial + tailOffset;
    }
}
