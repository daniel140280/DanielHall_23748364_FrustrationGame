package gameobserver;

import players.Player;

public interface GameListener {
    void onSuccessfulMove(Player player, int fromPosition, int toPosition, int roll);
    void onBlockedMove(Player player, int attemptedPosition, int roll);
    void onEndReached(Player player, int attemptedPosition, int overshoot);
    void onGameOver(Player[] players);
}
