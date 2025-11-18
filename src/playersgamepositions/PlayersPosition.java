package playersgamepositions;

import players.Player;

import java.util.Objects;

/*
Value Object used to track the position of the players as they traverse the board.
Strategies will determine whether board positions are valid or forfeit.
History will be kept to track game statistics.
Observer will listen to position progress and print relevant information to the console.
 */
public class PlayersPosition {
    private int boardIndex;                              //Tracking current board index based on the player in context.
    private boolean indexInTail;                         //Supporting whether player is in their tail position.
    private final Player player;

    public PlayersPosition(Player player){
        this.player = player;
        this.boardIndex = player.getStartIndex();       //When a player is created, we automatically assign their starting board index position.
        this.indexInTail = false;
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

    public Player getPlayer() {
        return player;
    }

    @Override
    public String toString() {
        return indexInTail ? player.getName().charAt(0) + String.valueOf(boardIndex - player.getTailStartIndex() + 1) : String.valueOf(boardIndex);
    }


}
