package gamestrategies.hitimplementations;

import gamestrategies.HitStrategy;
import players.Player;
import playersgamepositions.PlayersInGameContext;

import java.util.Map;

public class AllowHitStrategy implements HitStrategy {
    @Override
    public boolean canMoveToPosition(Player currentPlayer, int targetIndex, Map<Player, PlayersInGameContext> allPlayers) {
        return true; // Always allow move, even if another player is there
    }
}
