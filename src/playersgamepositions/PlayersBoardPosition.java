package playersgamepositions;

import players.Player;

/*
Value Object used to track the position of the players as they traverse the board.
Strategies will determine whether board positions are valid or forfeit.
History will be kept to track game statistics.
Observer will listen to position progress and print relevant information to the console.
 */
public class PlayersBoardPosition {
    private int boardIndex;                              //Tracking current board index based on the player in context.
    private boolean indexInTail;                         //Supporting whether player is in their tail position.

    public PlayersBoardPosition(Player player){
        this.boardIndex = player.getStartIndex();       //When a player is created, we automatically assign their starting board index position.
    }
}
