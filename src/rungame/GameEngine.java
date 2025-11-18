package rungame;

import board.GameBoard;
import dice.DiceShaker;
import gameobserver.GameListener;
import gamestrategies.EndStrategy;
import gamestrategies.HitStrategy;
import players.Player;
import playersgamepositions.PlayersInGameContext;
import playersgamepositions.PlayersMoveHistory;
import playersgamepositions.PlayersPosition;
import playersgamepositions.StandardMoveStrategy;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GameEngine {
    private final Player[] players;
    private final GameBoard board;
    private final DiceShaker dice;
    private final List<GameListener> listeners;
    private final Map<Player, PlayersInGameContext> playerContexts = new LinkedHashMap<>();
    private final StandardMoveStrategy moveStrategy;

    public GameEngine(Player[] players, GameBoard board, DiceShaker dice,
                      HitStrategy hitStrategy, EndStrategy endStrategy, List<GameListener> listeners) {
        this.players = players;
        this.board = board;
        this.dice = dice;
        this.listeners = listeners;

        // Initialize player contexts
        for (Player player : players) {
            PlayersPosition position = new PlayersPosition(player);
            PlayersMoveHistory history = new PlayersMoveHistory();
            playerContexts.put(player, new PlayersInGameContext(position, history));
        }

        this.moveStrategy = new StandardMoveStrategy(board, hitStrategy, endStrategy, listeners, playerContexts);
    }

    public void playGame() {
        boolean gameOver = false;
        while (!gameOver) {
            for (Player player : players) {
                PlayersInGameContext context = playerContexts.get(player);
                if (context.getPlayersPosition().getBoardIndex() >= player.getTailEndIndex()) {
                    continue; // Skip if player already finished
                }

                int roll = dice.shake();
                moveStrategy.move(context, roll);

                if (context.getPlayersPosition().getBoardIndex() >= player.getTailEndIndex()) {
                    gameOver = true;
                    break;
                }
            }
        }

        // Notify listeners
        for (GameListener listener : listeners) {
            listener.onGameOver(players, playerContexts);
        }
    }

}
