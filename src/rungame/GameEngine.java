package rungame;

import board.GameBoard;
import dice.DiceShaker;
import gameconfig.*;
import gameobserver.GameListener;
import gamestrategies.EndStrategy;
import gamestrategies.HitStrategy;
import players.Player;
import playersgamepositions.PlayersInGameContext;
import playersgamepositions.PlayersMoveHistory;
import playersgamepositions.PlayersPosition;
import playersgamepositions.StandardMoveStrategy;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * GameEngine runs the game loop.
 * Accepts GameConfiguration (DIP, SOLID).
 * - Winner tracking added.
 * - Tail check requires isInTail() AND >= tailEndIndex.
 */
public class GameEngine {
    private final Player[] players;
    private final GameBoard board;
    private final DiceShaker dice;
    private final EndStrategy endStrategy;
    private final List<GameListener> listeners;
    private final Map<Player, PlayersInGameContext> playerContexts = new LinkedHashMap<>();
    private final StandardMoveStrategy moveStrategy;
    private Player winner;
    public Player getWinner() { return winner; }

    /**
     * GameEngine runs the game loop.
     * Accepts GameConfiguration (DIP, SOLID).
     * - Winner tracking added.
     * - Tail check requires isInTail() AND >= tailEndIndex.
     */
    public GameEngine(GameConfiguration config) {
        this.players = config.getPlayers();
        this.board = config.getBoard();
        this.dice = config.getDice();
        this.endStrategy = config.getEndStrategy();
        this.listeners = config.getListeners();


        // Initialize player contexts
        for (Player player : players) {
            PlayersPosition position = new PlayersPosition(player, board);
            PlayersMoveHistory history = new PlayersMoveHistory();
            playerContexts.put(player, new PlayersInGameContext(position, history));
        }

        this.moveStrategy = new StandardMoveStrategy(
                board,
                config.getHitStrategy(),
                config.getEndStrategy(),
                listeners,
                playerContexts
        );
    }

    public GameEngine(PlayerOption playerOpt,
                      DiceOption diceOpt,
                      BoardOption boardOpt,
                      HitOption hitOpt,
                      EndOption endOpt,
                      List<GameListener> listeners) {
        this(new GameConfiguration(playerOpt, diceOpt, boardOpt, hitOpt, endOpt, listeners));
    }

    public void playGame() {
        winner = null;
        boolean gameOver = false;
        while (!gameOver) {
            for (Player player : players) {
                PlayersInGameContext context = playerContexts.get(player);
                // Skip finished players
                if (context.isFinished()) {
                    continue;
                }
                // Skip if player already finished (should have already stopped if so?)
//                int tailEndIndex = board.getBoardLength() + board.getTailEndLength() - 1;
//                if (context.getPlayersPosition().isInTail() &&
//                        context.getPlayersPosition().getBoardIndex() >= tailEndIndex) {
//                    continue;
//                }
                int roll = dice.shake();
                moveStrategy.move(context, roll);
// REQUIRED CHANGE: Only check win condition if move was successful and player hasn't forfeited.
                //Delegates win condition to the End Strategy to determine - SRP!
                if (endStrategy.hasReachedEnd(player, context.getPlayersPosition().getBoardIndex())) {
                    context.setFinished(true);
                    winner = player;
                    gameOver = true;
                    break;
                }
            }
        }
        // Notify listeners
        for (GameListener listener : listeners) {
            listener.onGameOver(players, playerContexts);
        }
        if (winner != null) {
            System.out.println("\n🏆 Winner: " + winner.getColorCode() + winner.getName() + "\u001B[0m");
        }
    }
}
