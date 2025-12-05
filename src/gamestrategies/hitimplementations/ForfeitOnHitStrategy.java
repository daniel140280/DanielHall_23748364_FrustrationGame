package gamestrategies.hitimplementations;

import board.GameBoard;
import gamestrategies.HitStrategy;
import players.Player;
import playersgamepositions.PlayersInGameContext;

import java.util.Map;

//public class ForfeitOnHitStrategy implements HitStrategy {
////    private final GameBoard board;
////
////    public ForfeitOnHitStrategy(GameBoard board) {
////        this.board = board;
////    }
//
//    @Override
//    public boolean canMoveToPosition(Player currentPlayer, int targetIndex, Map<Player, PlayersInGameContext> allPlayers, GameBoard board) {
//        int boardLength = board.getBoardLength();
//
//        //If target is in their tail, no collision possible.
//        if (targetIndex >= boardLength) {
//            return true;
//        }
//        //Then calculate absolute position on the circular board.
//        int myAbsPos = (currentPlayer.getStartIndex() + targetIndex) % boardLength;
//
//        for (Map.Entry<Player, PlayersInGameContext> entry : allPlayers.entrySet()) {
//            Player otherPlayer = entry.getKey();
//            if (otherPlayer.equals(currentPlayer)) continue;
//
//            int otherIndex = entry.getValue().getPlayersPosition().getBoardIndex();
//
//            // If other player is in their tail, they are safe
//            if (otherIndex >= boardLength) continue;
//
//            int otherAbsPos = (otherPlayer.getStartIndex() + otherIndex) % boardLength;
//
//            if (myAbsPos == otherAbsPos) {
//                return false; // Collision detected!
//            }
//        }
//        return true;
//    }
//}


//            if (!entry.getKey().equals(currentPlayer)) {
//                if (entry.getValue().getPlayersPosition().getBoardIndex() == targetIndex) {
//                    return false; // Hit detected, move forfeited
//                }
//            }
//        }
//        return true;
//    }
//}

//public class ForfeitOnHitStrategy implements HitStrategy {
//
//    public ForfeitOnHitStrategy() {}
//
//    @Override
//    public boolean canMoveToPosition(Player currentPlayer, int targetIndex, Map<Player, PlayersInGameContext> allPlayers, GameBoard board) {
//        int boardLength = board.getBoardLength();
//
//        if (targetIndex >= boardLength) return true;
//
//        int myAbsPos = (currentPlayer.getStartIndex() + targetIndex) % boardLength;
//
//        for (Map.Entry<Player, PlayersInGameContext> entry : allPlayers.entrySet()) {
//            Player other = entry.getKey();
//            if (other.equals(currentPlayer)) continue;
//            int otherIndex = entry.getValue().getPlayersPosition().getBoardIndex();
//            if (otherIndex >= boardLength) continue;
//            int otherAbsPos = (other.getStartIndex() + otherIndex) % boardLength;
//
//            if (myAbsPos == otherAbsPos) return false;
//        }
//        return true;
//    }
//}

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