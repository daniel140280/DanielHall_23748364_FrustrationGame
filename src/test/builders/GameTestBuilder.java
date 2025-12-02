package test.builders;

import board.GameBoard;
import board.SmallGameBoard;
import dice.DiceShaker;
import dice.FixedDiceShaker;
import gameobserver.GameListener;
import gamestrategies.EndStrategy;
import gamestrategies.HitStrategy;
import gamestrategies.endimplementations.ExactEndStrategy;
import gamestrategies.hitimplementations.ForfeitOnHitStrategy;
import players.BluePlayer;
import players.Player;
import players.RedPlayer;
import rungame.GameConfiguration;
import rungame.GameEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Test builder for easier game setup in tests.
 * Provides sensible defaults and fluent API.
 */
public class GameTestBuilder {
    private GameBoard board = new SmallGameBoard();
    private Player[] players = new Player[]{ new RedPlayer(), new BluePlayer() };
    private DiceShaker dice = new FixedDiceShaker(1, 1, 1, 1);
    private EndStrategy endStrategy;
    private HitStrategy hitStrategy;
    private List<GameListener> listeners = new ArrayList<>();

    public GameTestBuilder() {
        this.endStrategy = new ExactEndStrategy(board);
        this.hitStrategy = new ForfeitOnHitStrategy();
    }

    public GameTestBuilder withBoard(GameBoard board) {
        this.board = board;
        this.endStrategy = new ExactEndStrategy(board); // Update strategy
        return this;
    }

    public GameTestBuilder withPlayers(Player... players) {
        this.players = players;
        return this;
    }

    public GameTestBuilder withDiceRolls(Integer... rolls) {
        this.dice = new FixedDiceShaker(rolls);
        return this;
    }

    public GameTestBuilder withEndStrategy(EndStrategy strategy) {
        this.endStrategy = strategy;
        return this;
    }

    public GameTestBuilder withHitStrategy(HitStrategy strategy) {
        this.hitStrategy = strategy;
        return this;
    }

    public GameTestBuilder addListener(GameListener listener) {
        this.listeners.add(listener);
        return this;
    }

    public GameEngine build() {
        GameConfiguration config = new GameConfiguration(
                players, board, dice, endStrategy, hitStrategy, listeners
        );
        return new GameEngine(config);
    }

    public GameEngine buildAndPlay() {
        GameEngine engine = build();
        engine.playGame();
        return engine;
    }
}