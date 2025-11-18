package runsimulations;

import board.BoardEnums;
import board.GameBoard;
import dice.DiceEnums;
import dice.DiceShaker;
import gameobserver.GameListener;
import gamesimulationsetup.BasicTwoPlayerSimulation;
import gamesimulationsetup.GameSimulationFactory;
import gamestrategies.EndStrategy;
import gamestrategies.endimplementations.ExactEndStrategy;
import gamestrategies.endimplementations.OvershootAllowedStrategy;
import gamestrategies.HitStrategy;
import gamestrategies.hitimplementations.AllowHitStrategy;
import gamestrategies.hitimplementations.ForfeitOnHitStrategy;
import gamestrategies.RuleSet;
import players.Player;
import players.PlayerEnums;
import rungame.GameEngine;

import java.util.List;


public class RunGameSimulations {
    /*
    The method is a factory method, bringing all the Game set-up requirements together to run the various Game Simulations.
     */
    public void runAllGameSimulations(){

        System.out.println("Basic game - 2 players, 2 dice, small gameboard. End does not need to be exact, and Hit is allowed.");
        System.out.println("Running: Basic 2-player game with double dice, small board, hit allowed, overshoot allowed.");

        GameSimulationFactory factory = new BasicTwoPlayerSimulation();

        Player[] players = factory.createPlayers(PlayerEnums.TWO_PLAYER);
        GameBoard board = factory.createBoard(BoardEnums.SMALL);
        DiceShaker dice = factory.createDice(DiceEnums.DOUBLE);
        RuleSet rules = factory.defineRules();
        List<GameListener> listeners = factory.setupListeners();

        HitStrategy hitStrategy = rules.allowsPlayerHit() ? new AllowHitStrategy() : new ForfeitOnHitStrategy();
        EndStrategy endStrategy = rules.requireExactEnd() ? new ExactEndStrategy() : new OvershootAllowedStrategy();

        GameEngine engine = new GameEngine(players, board, dice, hitStrategy, endStrategy, listeners);
        engine.playGame();


    }
}
