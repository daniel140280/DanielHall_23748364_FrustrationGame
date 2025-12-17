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
        // Red starts at 0, Blue starts at 9. Small Board (18 shared).
        // Roll 1 (Red): 12 -> Pos 12
        // Roll 2 (Blue): 12 -> 9 + 12 = 21 (Steps taken: 12).
        // Note: 18 shared + 3 tail = 21 total. Index 20 is "End".
        // Blue reaches index 21 on the second roll and wins via Overshoot!

        new GameTestBuilder()
                .withBoard(new SmallGameBoard())
                .withPlayers(new RedPlayer(), new BluePlayer())
                .withDiceRolls(12, 12, 7, 8) // Red, Blue, Red, Blue
                .withHitStrategy(new AllowHitStrategy())
                .withEndStrategy(new OvershootAllowedStrategy(new SmallGameBoard()))
                .addListener(new ObserverConsoleLogger())
                .buildAndPlay();
    }

    public void runScenarioTwo() {
        System.out.println("\n=== SCENARIO 2: Blue Wins (Dice Roll sequence - 12, 12, 6, 6, 2) ===");
        // Roll 1 (Red): 12 -> Pos 12
        // Roll 2 (Blue): 12 -> Pos 21 (Wins immediately)
        // Wait: If Blue rolls a 12, they win on turn 2. To test a longer sequence,
        // we need rolls that keep them on the board longer.

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
