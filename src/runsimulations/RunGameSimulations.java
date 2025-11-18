package runsimulations;

import board.BoardEnums;
import board.GameBoard;
import boardfactory.GameBoardFactory;
import dice.DiceEnums;
import dice.DiceShaker;
import dice.RandomDoubleDiceShaker;
import gameobserver.GameListener;
import gameobserver.ObserverConsoleLogger;
import gamestrategies.EndStrategy;
import gamestrategies.HitStrategy;
import gamestrategies.RuleSet;
import gamestrategies.endimplementations.ExactEndStrategy;
import gamestrategies.endimplementations.OvershootAllowedStrategy;
import gamestrategies.hitimplementations.AllowHitStrategy;
import gamestrategies.hitimplementations.ForfeitOnHitStrategy;
import players.BluePlayer;
import players.Player;
import players.RedPlayer;
import rungame.GameEngine;

import java.util.List;

public class RunGameSimulations {
    /*
    The method is a factory method, bringing all the Game set-up requirements together to run the various Game Simulations.
     */
    public void runAllGameSimulations(){

        System.out.println("Basic game - 2 players, 2 dice, small gameboard. End does not need to be exact, and Hit is allowed.");
        // 1. RuleSet
        RuleSet ruleSet = new RuleSet(false, true);

        // 2. Strategies
        HitStrategy hitStrategy = ruleSet.allowsPlayerHit() ? new AllowHitStrategy() : new ForfeitOnHitStrategy();
        EndStrategy endStrategy = ruleSet.requireExactEnd() ? new ExactEndStrategy() : new OvershootAllowedStrategy();

        // 3. Game setup
        Player[] players = new Player[]{new RedPlayer(), new BluePlayer()};
        GameBoard board = GameBoardFactory.createBoard(BoardEnums.SMALL);
        DiceShaker dice = new RandomDoubleDiceShaker();
        List<GameListener> listeners = List.of(new ObserverConsoleLogger());

        // 4. Run game
        GameEngine engine = new GameEngine(players, board, dice, hitStrategy, endStrategy, listeners);
        engine.playGame();



    }
}
