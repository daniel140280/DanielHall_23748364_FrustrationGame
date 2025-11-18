package gamestrategies.hitimplementations;

import gamestrategies.HitStrategy;
import players.Player;
import playersgamepositions.PlayersInGameContext;

import java.util.Map;

public class ForfeitOnHitStrategy implements HitStrategy {
    @Override
    public boolean canMoveToPosition(Player currentPlayer, int targetIndex, Map<Player, PlayersInGameContext> allPlayers) {
        for (Map.Entry<Player, PlayersInGameContext> entry : allPlayers.entrySet()) {
            if (!entry.getKey().equals(currentPlayer)) {
                if (entry.getValue().getPlayersPosition().getBoardIndex() == targetIndex) {
                    return false; // Hit detected, move forfeited
                }
            }
        }
        return true;
    }
}
