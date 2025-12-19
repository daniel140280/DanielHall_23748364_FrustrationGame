package gamestate;

import playersgamepositions.PlayersInGameContext;
import rungame.GameEngine;

public class ReadyState implements GameState{
    @Override
    public void handleDiceRoll(GameEngine context, PlayersInGameContext playerContext, int roll) {
        // Transition to In Play
        context.setGameState(new InPlayState());

        context.takeTurn(playerContext, roll);
    }
    @Override
    public String toString() {
        return "Ready";
    }
}
