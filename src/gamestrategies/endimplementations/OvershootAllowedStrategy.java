package gamestrategies.endimplementations;

import gamestrategies.EndStrategy;
import players.Player;

/**
 * OvershootAllowedStrategy lets players finish even if they roll past their tail end.
 */

public class OvershootAllowedStrategy implements EndStrategy {
    @Override
    public boolean hasReachedEnd(Player player, int currentIndex) {
        return currentIndex >= player.getTailEndIndex();
    }

    @Override
    public int calculateOvershoot(Player player, int currentIndex) {
        return Math.max(0, currentIndex - player.getTailEndIndex());
    }
}
