package rungame;

import board.GameBoard;
import dice.DiceShaker;
import gameobserver.GameListener;
import gamestrategies.EndStrategy;
import gamestrategies.HitStrategy;
import players.Player;

import java.util.List;

/**
 * Single transparent object for all setup.
 * Holds players, board, dice, strategies, listeners.
 */

public class GameConfiguration {
    private final Player[] players;
    private final GameBoard board;
    private final DiceShaker dice;
    private final EndStrategy endStrategy;
    private final HitStrategy hitStrategy;
    private final List<GameListener> listeners;

    public GameConfiguration(Player[] players, GameBoard board, DiceShaker dice,
                             EndStrategy endStrategy, HitStrategy hitStrategy,
                             List<GameListener> listeners) {
        this.players = players;
        this.board = board;
        this.dice = dice;
        this.endStrategy = endStrategy;
        this.hitStrategy = hitStrategy;
        this.listeners = listeners;
    }


    // Getters
    public Player[] getPlayers() {
        return players;
    }

    public GameBoard getBoard() {
        return board;
    }

    public DiceShaker getDice() {
        return dice;
    }

    public HitStrategy getHitStrategy() {
        return hitStrategy;
    }

    public EndStrategy getEndStrategy() {
        return endStrategy;
    }

    public List<GameListener> getListeners() {
        return listeners;
    }
}
