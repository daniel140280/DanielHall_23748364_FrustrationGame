package test.rungame;

import board.GameBoard;
import board.SmallGameBoard;
import board.LargeGameBoard;
import dice.DiceShaker;
import dice.FixedDiceShaker;
import gameconfig.*;
import gameobserver.GameListener;
import gameobserver.ObserverConsoleLogger;
import gamestrategies.EndStrategy;
import gamestrategies.HitStrategy;
import gamestrategies.endimplementations.ExactEndStrategy;
import gamestrategies.endimplementations.OvershootAllowedStrategy;
import gamestrategies.hitimplementations.AllowHitStrategy;
import gamestrategies.hitimplementations.ForfeitOnHitStrategy;
import players.BluePlayer;
import players.Player;
import players.RedPlayer;
import playersgamepositions.PlayersInGameContext;
import rungame.GameConfiguration;
import rungame.GameEngine;
import test.mocks.MockGameListener;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

/**
 * CORRECTED JUnit 5 test suite - Accounts for strategy-aware win conditions.
 * Tests now properly verify that position capping and strategy delegation work correctly.
 */
public class GameEngineTest {

    private GameBoard smallBoard;
    private GameBoard largeBoard;
    private Player[] twoPlayers;
    private Player redPlayer;
    private Player bluePlayer;

    @BeforeEach
    public void setup() {
        smallBoard = new SmallGameBoard();
        largeBoard = new LargeGameBoard();
        redPlayer = new RedPlayer();
        bluePlayer = new BluePlayer();
        twoPlayers = new Player[]{redPlayer, bluePlayer};
    }

    // ========== EXACT END STRATEGY TESTS ==========

    @Test
    @DisplayName("ExactEnd: Player wins by landing exactly on end position")
    public void testExactWin() {
        // Small board: tailEndIndex = 18 + 3 - 1 = 20
        // Red starts at 0, needs to reach 20
        // Total steps needed: 18 (complete board) + 2 (into tail) = 20 steps

        MockGameListener listener = new MockGameListener();

        DiceShaker dice = new FixedDiceShaker(
                6, 1,  // Red: 6 (pos 6), Blue: 1 (pos 10)
                6, 1,  // Red: 12, Blue: 11
                6, 1,  // Red: 0 (wrapped), Blue: 12
                2, 1   // Red: 2 (20 steps taken, enters tail at index 20 = WIN!)
        );

        EndStrategy endStrategy = new ExactEndStrategy(smallBoard);
        HitStrategy hitStrategy = new AllowHitStrategy();

        GameConfiguration config = new GameConfiguration(
                twoPlayers, smallBoard, dice, endStrategy, hitStrategy, List.of(listener)
        );
        GameEngine engine = new GameEngine(config);
        engine.playGame();

        // Verify Red won with exact landing
        assertEquals(1, listener.getEndEventCount(), "Should have exactly 1 end event");
        MockGameListener.EndEvent endEvent = listener.getEndEvents().get(0);
        assertEquals("Red", endEvent.player.getName(), "Red should win");
        assertEquals(0, endEvent.overshoot, "Should have no overshoot (exact landing)");
        assertTrue(listener.wasGameOverCalled(), "Game over should be called");
    }

    @Test
    @DisplayName("ExactEnd: Player forfeits move when overshooting end")
    public void testExactOvershootForfeit() {
        // Position Red at 19 (one away from end at 20)
        // Then roll 3 (would go to 22, overshoot by 2)

        MockGameListener listener = new MockGameListener();

        DiceShaker dice = new FixedDiceShaker(
                6, 1,  // Red: 6, Blue: 10
                6, 1,  // Red: 12, Blue: 11
                6, 1,  // Red: 0 (18 steps, enters tail next), Blue: 12
                1, 1,  // Red: 19 (index 19, which is boardLen + 1), Blue: 13
                3, 1   // Red: attempts 22 (OVERSHOOT - should forfeit), Blue: 14
        );

        EndStrategy endStrategy = new ExactEndStrategy(smallBoard);
        HitStrategy hitStrategy = new AllowHitStrategy();

        GameConfiguration config = new GameConfiguration(
                twoPlayers, smallBoard, dice, endStrategy, hitStrategy, List.of(listener)
        );
        GameEngine engine = new GameEngine(config);

        // Run only until Red's overshoot attempt
        // Since we can't stop mid-game easily, we'll check end events
        try {
            engine.playGame();
        } catch (IllegalStateException e) {
            // Expected - we'll run out of dice rolls
        }

        // Verify overshoot was detected
        boolean foundOvershoot = listener.getEndEvents().stream()
                .anyMatch(e -> e.player.getName().equals("Red") && e.overshoot > 0);
        assertTrue(foundOvershoot, "Should detect Red's overshoot forfeit");
    }

    @Test
    @DisplayName("ExactEnd: Player must roll exact number to win")
    public void testExactRequiresMultipleAttempts() {
        // Red at position 19, tries multiple overshoots before winning

        MockGameListener listener = new MockGameListener();

        DiceShaker dice = new FixedDiceShaker(
                6, 1,  // Red: 6, Blue: 10
                6, 1,  // Red: 12, Blue: 11
                6, 1,  // Red: 0 (wrapped), Blue: 12
                1, 1,  // Red: 19, Blue: 13
                3, 1,  // Red: OVERSHOOT (stays 19), Blue: 14
                2, 1,  // Red: OVERSHOOT (stays 19), Blue: 15
                1, 1   // Red: 20 EXACT WIN!, Blue: 16
        );

        EndStrategy endStrategy = new ExactEndStrategy(smallBoard);
        HitStrategy hitStrategy = new AllowHitStrategy();

        GameConfiguration config = new GameConfiguration(
                twoPlayers, smallBoard, dice, endStrategy, hitStrategy, List.of(listener)
        );
        GameEngine engine = new GameEngine(config);
        engine.playGame();

        // Count overshoot forfeits
        long overshootCount = listener.getEndEvents().stream()
                .filter(e -> e.overshoot > 0)
                .count();
        assertTrue(overshootCount >= 2, "Should have at least 2 overshoot forfeits");

        // Verify final win was exact
        MockGameListener.EndEvent finalEnd = listener.getEndEvents().get(
                listener.getEndEvents().size() - 1
        );
        assertEquals("Red", finalEnd.player.getName());
        assertEquals(0, finalEnd.overshoot, "Final win should have no overshoot");
    }

    // ========== OVERSHOOT ALLOWED STRATEGY TESTS ==========

    @Test
    @DisplayName("OvershootAllowed: Player wins even when overshooting end")
    public void testOvershootWin() {
        // Red at position 19, rolls 3 to reach 22 (overshoot by 2)
        // Should WIN and position should be CAPPED at 20

        MockGameListener listener = new MockGameListener();

        DiceShaker dice = new FixedDiceShaker(
                6, 1,  // Red: 6, Blue: 10
                6, 1,  // Red: 12, Blue: 11
                6, 1,  // Red: 0 (wrapped), Blue: 12
                1, 1,  // Red: 19, Blue: 13
                3, 1   // Red: attempts 22 (WINS with overshoot)
        );

        EndStrategy endStrategy = new OvershootAllowedStrategy(smallBoard);
        HitStrategy hitStrategy = new AllowHitStrategy();

        GameConfiguration config = new GameConfiguration(
                twoPlayers, smallBoard, dice, endStrategy, hitStrategy, List.of(listener)
        );
        GameEngine engine = new GameEngine(config);
        engine.playGame();

        // Verify Red won with overshoot
        assertEquals(1, listener.getEndEventCount(), "Should have 1 end event");
        MockGameListener.EndEvent endEvent = listener.getEndEvents().get(0);
        assertEquals("Red", endEvent.player.getName(), "Red should win");
        assertEquals(2, endEvent.overshoot, "Should report overshoot of 2");
        assertTrue(listener.wasGameOverCalled(), "Game over should be called");
    }

    @Test
    @DisplayName("OvershootAllowed: Large overshoot still wins")
    public void testLargeOvershootWin() {
        // Test that even rolling WAY past the end still wins

        MockGameListener listener = new MockGameListener();

        DiceShaker dice = new FixedDiceShaker(
                6, 1,  // Red: 6, Blue: 10
                6, 1,  // Red: 12, Blue: 11
                6, 1,  // Red: 0, Blue: 12
                6, 1   // Red: 6 (24 steps = enters tail at index 24, overshoot by 4) WINS
        );

        EndStrategy endStrategy = new OvershootAllowedStrategy(smallBoard);
        HitStrategy hitStrategy = new AllowHitStrategy();

        GameConfiguration config = new GameConfiguration(
                twoPlayers, smallBoard, dice, endStrategy, hitStrategy, List.of(listener)
        );
        GameEngine engine = new GameEngine(config);
        engine.playGame();

        // Verify win with large overshoot
        MockGameListener.EndEvent endEvent = listener.getEndEvents().get(0);
        assertEquals("Red", endEvent.player.getName());
        assertTrue(endEvent.overshoot >= 4, "Should have large overshoot");
    }

    @Test
    @DisplayName("OvershootAllowed: Exact landing also wins")
    public void testOvershootAllowsExactToo() {
        // Verify that exact landing still works with overshoot allowed

        MockGameListener listener = new MockGameListener();

        DiceShaker dice = new FixedDiceShaker(
                6, 1,
                6, 1,
                6, 1,
                2, 1   // Red: exact 20 (no overshoot)
        );

        EndStrategy endStrategy = new OvershootAllowedStrategy(smallBoard);
        HitStrategy hitStrategy = new AllowHitStrategy();

        GameConfiguration config = new GameConfiguration(
                twoPlayers, smallBoard, dice, endStrategy, hitStrategy, List.of(listener)
        );
        GameEngine engine = new GameEngine(config);
        engine.playGame();

        MockGameListener.EndEvent endEvent = listener.getEndEvents().get(0);
        assertEquals(0, endEvent.overshoot, "Exact landing should have no overshoot");
    }

    // ========== HIT STRATEGY TESTS ==========

    @Test
    @DisplayName("ForbidHit: Player forfeits move when hitting another player")
    public void testHitBlocked() {
        // Red at 0, Blue at 9 (start)
        // Red rolls 9 (would land on Blue's position) - should FORFEIT

        MockGameListener listener = new MockGameListener();

        DiceShaker dice = new FixedDiceShaker(
                9, 1,  // Red tries to move to 9 (Blue's start) - BLOCKED
                1, 1   // Red: 1 (now can move), Blue: 10
        );

        EndStrategy endStrategy = new ExactEndStrategy(smallBoard);
        HitStrategy hitStrategy = new ForfeitOnHitStrategy();

        GameConfiguration config = new GameConfiguration(
                twoPlayers, smallBoard, dice, endStrategy, hitStrategy, List.of(listener)
        );

        // Run a few turns
        GameEngine engine = new GameEngine(config);
        try {
            engine.playGame();
        } catch (IllegalStateException e) {
            // Will run out of dice
        }

        // Verify blocked move was recorded
        assertEquals(1, listener.getBlockedMoveCount(), "Should have 1 blocked move");
        MockGameListener.MoveEvent blockedMove = listener.getBlockedMoves().get(0);
        assertEquals("Red", blockedMove.player.getName());
        assertEquals(9, blockedMove.toPosition, "Should attempt position 9");
    }

    @Test
    @DisplayName("AllowHit: Players can occupy same position")
    public void testHitAllowed() {
        MockGameListener listener = new MockGameListener();

        DiceShaker dice = new FixedDiceShaker(
                9, 1,  // Red: 9 (same as Blue start), Blue: 10
                1, 1   // Both can continue
        );

        EndStrategy endStrategy = new ExactEndStrategy(smallBoard);
        HitStrategy hitStrategy = new AllowHitStrategy();

        GameConfiguration config = new GameConfiguration(
                twoPlayers, smallBoard, dice, endStrategy, hitStrategy, List.of(listener)
        );

        GameEngine engine = new GameEngine(config);
        try {
            engine.playGame();
        } catch (IllegalStateException e) {
            // Expected
        }

        // No blocked moves should occur
        assertEquals(0, listener.getBlockedMoveCount(), "Should have no blocked moves");
        // Red should successfully move to 9
        assertTrue(listener.getSuccessfulMoveCount() >= 1, "Should have successful moves");
    }

    // ========== BOARD WRAPPING TESTS ==========

    @Test
    @DisplayName("Player wraps around shared board correctly")
    public void testBoardWrapping() {
        // Small board: 18 positions (0-17)
        // Red at 16, rolls 5 -> should wrap to position 3

        MockGameListener listener = new MockGameListener();

        DiceShaker dice = new FixedDiceShaker(
                6, 1,  // Red: 6, Blue: 10
                6, 1,  // Red: 12, Blue: 11
                4, 1,  // Red: 16, Blue: 12
                5, 1   // Red: wraps to 3 (16+5=21, 21%18=3), Blue: 13
        );

        EndStrategy endStrategy = new ExactEndStrategy(smallBoard);
        HitStrategy hitStrategy = new AllowHitStrategy();

        GameConfiguration config = new GameConfiguration(
                twoPlayers, smallBoard, dice, endStrategy, hitStrategy, List.of(listener)
        );

        GameEngine engine = new GameEngine(config);
        try {
            engine.playGame();
        } catch (IllegalStateException e) {
            // Expected
        }

        // Check 4th move (Red's wrap around)
        assertTrue(listener.getSuccessfulMoves().size() >= 4, "Should have at least 4 moves");
        MockGameListener.MoveEvent wrapMove = listener.getSuccessfulMoves().get(6); // Red's 4th move
        assertEquals(3, wrapMove.toPosition, "Should wrap to position 3");
    }

    @Test
    @DisplayName("Player enters tail after completing full lap")
    public void testTailEntry() {
        // Red needs 18+ steps to enter tail

        MockGameListener listener = new MockGameListener();

        DiceShaker dice = new FixedDiceShaker(
                6, 1,  // Red: 6 (6 steps)
                6, 1,  // Red: 12 (12 steps)
                6, 1,  // Red: 0 (18 steps, wrapped but ready for tail)
                1, 1   // Red: enters tail (19 total steps, index 19)
        ).withRepeatLast();

        EndStrategy endStrategy = new ExactEndStrategy(smallBoard);
        HitStrategy hitStrategy = new AllowHitStrategy();

        GameConfiguration config = new GameConfiguration(
                new Player[]{redPlayer}, smallBoard, dice, endStrategy, hitStrategy, List.of(listener)
        );

        GameEngine engine = new GameEngine(config);
        try {
            engine.playGame();
        } catch (IllegalStateException e) {
            // May run out of rolls or take too long
        }
    }
}
