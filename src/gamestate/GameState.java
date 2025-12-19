package gamestate;

import playersgamepositions.PlayersInGameContext;
import rungame.GameEngine;

public interface GameState {
    void handleDiceRoll(GameEngine context, PlayersInGameContext playerContext, int roll);
    String toString();
}
