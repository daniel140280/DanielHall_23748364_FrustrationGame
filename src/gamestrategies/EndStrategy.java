package gamestrategies;

import players.Player;
/**
 * EndStrategy defines how a player finishes the game.
 */

public interface EndStrategy {
    boolean hasReachedEnd(Player player, int currentIndex);
    int calculateOvershoot(Player player, int currentIndex);
}
