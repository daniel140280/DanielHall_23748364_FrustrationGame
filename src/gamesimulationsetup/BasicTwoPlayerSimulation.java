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

public class BasicTwoPlayerSimulation implements GameSimulationFactory {
    @Override
    public Player[] createPlayers(PlayerEnums playerType) {
        if (playerType == PlayerEnums.TWO_PLAYER) {
            return new Player[]{new RedPlayer(), new BluePlayer()};
        }
        throw new UnsupportedOperationException("Only TWO_PLAYER supported in this simulation.");
    }

    @Override
    public GameBoard createBoard(BoardEnums boardSize) {
        return GameBoardFactory.createBoard(boardSize);
    }

    @Override
    public DiceShaker createDice(DiceEnums diceType) {
        return new RandomDoubleDiceShaker();
    }

    @Override
    public RuleSet defineRules() {
        return new RuleSet(false, true); // Not exact end, hit allowed
    }

    @Override
    public List<GameListener> setupListeners() {
        return List.of(new ObserverConsoleLogger()); // Will be updated to accept context if needed
    }

}
