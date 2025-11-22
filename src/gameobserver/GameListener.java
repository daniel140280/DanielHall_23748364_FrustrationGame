package gameobserver;

import players.Player;
import playersgamepositions.PlayersInGameContext;

import java.util.Map;

public interface GameListener {
//    void onSuccessfulMove(Player player, int fromPosition, int toPosition, int roll);
//    void onBlockedMove(Player player, int attemptedPosition, int roll);
//    void onEndReached(Player player, int attemptedPosition, int overshoot);
//    void onGameOver(Player[] players);

    void onSuccessfulMove(Player player, PlayersInGameContext context, int fromPosition, int toPosition, int roll);
    void onBlockedMove(Player player, PlayersInGameContext context, int attemptedPosition, int roll);
    void onEndReached(Player player, PlayersInGameContext context, int attemptedPosition, int overshoot, int roll);
    void onGameOver(Player[] players, Map<Player, PlayersInGameContext> contexts);

}


