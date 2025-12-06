package gamestrategies.hitimplementations;

import board.GameBoard;
import gamestrategies.HitStrategy;
import players.Player;
import playersgamepositions.PlayersInGameContext;

import java.util.Map;

/**
 * ForfeitOnHitStrategy - Tail positions are LINEAR, not modular.
 */
public class ForfeitOnHitStrategy implements HitStrategy {

    @Override
    public boolean canMoveToPosition(Player currentPlayer, int targetIndex,
                                     Map<Player, PlayersInGameContext> allPlayers, GameBoard board) {
        int boardLength = board.getBoardLength();

        // If target is in tail, NO collision possible (separate tails)
        if (targetIndex >= boardLength) {
            return true;  // Always allow - players have separate tails
        }

        // Only check collisions on shared board
        for (Map.Entry<Player, PlayersInGameContext> entry : allPlayers.entrySet()) {
            Player other = entry.getKey();
            if (other.equals(currentPlayer)) continue;  // Skip self

            int otherIndex = entry.getValue().getPlayersPosition().getBoardIndex();

            // If other player is in tail, they can't collide with shared board
            if (otherIndex >= boardLength) continue;

            // Both players are on shared board - check if same position
            if (targetIndex == otherIndex) {
                return false;  // Collision detected!
            }
        }
        return true;  // No collision
    }
}