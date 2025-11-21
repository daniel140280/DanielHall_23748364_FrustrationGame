package gamestrategies.endimplementations;

import gamestrategies.EndStrategy;
import players.Player;

/**
 * ExactEndStrategy requires players to land exactly on their tail end.
 */

public class ExactEndStrategy implements EndStrategy {
    @Override
    public boolean hasReachedEnd(Player player, int currentIndex) {
        return currentIndex == player.getTailEndIndex();
    }

    @Override
    public int calculateOvershoot(Player player, int currentIndex) {
        return currentIndex > player.getTailEndIndex() ? currentIndex - player.getTailEndIndex() : 0;
    }

}
