package gamestrategies.hitimplementations;

import board.GameBoard;
import gamestrategies.HitStrategy;
import players.Player;
import playersgamepositions.PlayersInGameContext;

import java.util.Map;


public class AllowHitStrategy implements HitStrategy {
    @Override
    public boolean canMoveToPosition(Player currentPlayer, int targetIndex, Map<Player, PlayersInGameContext> allPlayers, GameBoard board) {
        // Always allow move. Board param included for interface consistency.
        return true;
    }
}