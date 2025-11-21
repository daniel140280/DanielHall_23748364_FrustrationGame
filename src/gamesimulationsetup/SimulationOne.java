package gamesimulationsetup;

import board.BoardEnums;
import board.GameBoard;
import boardfactory.GameBoardFactory;
import dice.DiceEnums;
import dice.DiceShaker;
import dice.RandomDoubleDiceShaker;
import gameobserver.GameListener;
import gameobserver.ObserverConsoleLogger;
import gamestrategies.RuleSet;
import players.BluePlayer;
import players.Player;
import players.PlayerEnums;
import players.RedPlayer;

import java.util.List;

//public class SimulationOne implements GameSimulationFactory{
//    @Override
//    public Player[] createPlayers(PlayerEnums playerType) {
//        if (playerType == PlayerEnums.TWO_PLAYER) {
//            return new Player[]{new RedPlayer(), new BluePlayer()};
//        }
//        throw new UnsupportedOperationException("Only TWO_PLAYER supported in this simulation.");
//    }
//
//    @Override
//    public GameBoard createBoard(BoardEnums boardSize) {
//        return GameBoardFactory.createBoard(boardSize);
//    }
//
//    @Override
//    public DiceShaker createDice(DiceEnums diceType) {
//        return new RandomDoubleDiceShaker();
//    }
//
//    @Override
//    public RuleSet defineRules() {
//        return new RuleSet(false, true); // Not exact end, hit allowed
//    }
//
//    @Override
//    public List<GameListener> setupListeners() {
//        return List.of(new ObserverConsoleLogger()); // Will be updated to accept context if needed
//    }
//
//}

//System.out.println("Running: Basic 2-players, 2-dice, small board game. Player hits allowed. End does note need to be exact.");
//
//GameSimulationFactory factory = new BasicTwoPlayerSimulation();
//
//Player[] players = factory.createPlayers(PlayerEnums.TWO_PLAYER);
//GameBoard board = factory.createBoard(BoardEnums.SMALL);
//DiceShaker dice = factory.createDice(DiceEnums.DOUBLE);
//RuleSet rules = factory.defineRules();
//List<GameListener> listeners = factory.setupListeners();
//
//HitStrategy hitStrategy = rules.allowsPlayerHit() ? new AllowHitStrategy() : new ForfeitOnHitStrategy();
//EndStrategy endStrategy = rules.requireExactEnd() ? new ExactEndStrategy() : new OvershootAllowedStrategy();
//
//GameEngine engine = new GameEngine(players, board, dice, hitStrategy, endStrategy, listeners);
//        engine.playGame();
