package gamestrategies.hitimplementations;

import board.GameBoard;
import gamestrategies.HitStrategy;
import players.Player;
import playersgamepositions.PlayersInGameContext;

import java.util.Map;

/**
 * FULLY FIXED ForfeitOnHitStrategy - Correct handling of tail positions.
 * KEY FIX: Tail positions are LINEAR, not circular! Can't use modular arithmetic.
 */
public class ForfeitOnHitStrategy implements HitStrategy {

    @Override
    public boolean canMoveToPosition(Player currentPlayer, int targetIndex,
                                     Map<Player, PlayersInGameContext> allPlayers, GameBoard board) {
        int boardLength = board.getBoardLength();

        // ✅ CRITICAL FIX: If target is in tail, NO collision possible (separate tails)
        if (targetIndex >= boardLength) {
            return true;  // Always allow - players have separate tails
        }

        // Only check collisions on shared board (indices 0 to boardLength-1)
        for (Map.Entry<Player, PlayersInGameContext> entry : allPlayers.entrySet()) {
            Player other = entry.getKey();
            if (other.equals(currentPlayer)) continue;  // Skip self

            int otherIndex = entry.getValue().getPlayersPosition().getBoardIndex();

            // ✅ FIXED: If other player is in tail, they can't collide with shared board
            if (otherIndex >= boardLength) continue;

            // Both players are on shared board - check if same position
            if (targetIndex == otherIndex) {
                return false;  // Collision detected!
            }
        }

        return true;  // No collision
    }
}