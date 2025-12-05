package gamestrategies;

import board.GameBoard;
import players.Player;
import playersgamepositions.PlayersInGameContext;

import java.util.Map;

//public interface HitStrategy {
//    boolean canMoveToPosition(Player currentPlayer, int targetIndex, Map<Player, PlayersInGameContext> allPlayers, GameBoard board);
//}

public interface HitStrategy {
    boolean canMoveToPosition(Player currentPlayer, int targetIndex, Map<Player, PlayersInGameContext> allPlayers, GameBoard board);
}