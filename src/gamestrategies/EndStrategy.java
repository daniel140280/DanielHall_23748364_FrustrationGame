package gamestrategies;

import players.Player;

public interface EndStrategy {
    boolean hasReachedEnd(Player player, int currentIndex);
    int calculateOvershoot(Player player, int currentIndex);

}
