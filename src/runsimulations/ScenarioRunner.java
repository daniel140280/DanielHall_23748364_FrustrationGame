package runsimulations;

import board.SmallGameBoard;
import gameobserver.ObserverConsoleLogger;
import gamestrategies.endimplementations.OvershootAllowedStrategy;
import gamestrategies.hitimplementations.AllowHitStrategy;
import players.BluePlayer;
import players.RedPlayer;
import rungame.GameEngine;
import test.builders.GameTestBuilder;
import java.util.List;

public class ScenarioRunner {

    public void runScenarioOne() {
        System.out.println("\n=== SCENARIO 1: Blue Wins (Dice Roll sequence - 12, 12, 7, 8) ===");

        new GameTestBuilder()
                .withBoard(new SmallGameBoard())
                .withPlayers(new RedPlayer(), new BluePlayer())
                .withDiceRolls(12, 12, 7, 8)
                .withHitStrategy(new AllowHitStrategy())
                .withEndStrategy(new OvershootAllowedStrategy(new SmallGameBoard()))
                .addListener(new ObserverConsoleLogger())
                .buildAndPlay();
    }

    public void runScenarioTwo() {
        System.out.println("\n=== SCENARIO 2: Blue Wins (Dice Roll sequence - 12, 12, 6, 6, 2) ===");

        new GameTestBuilder()
                .withBoard(new SmallGameBoard())
                .withPlayers(new RedPlayer(), new BluePlayer())
                .withDiceRolls(12, 12, 6, 6, 2)
                .withHitStrategy(new AllowHitStrategy())
                .withEndStrategy(new OvershootAllowedStrategy(new SmallGameBoard()))
                .addListener(new ObserverConsoleLogger())
                .buildAndPlay();
    }
}
