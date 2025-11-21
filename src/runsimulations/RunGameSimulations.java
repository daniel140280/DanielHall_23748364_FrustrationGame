package runsimulations;

import gamesimulationsetup.GameSimulationFactory;

import rungame.GameConfiguration;
import rungame.GameEngine;

import java.util.List;

/*
The method is a facade factory method, bringing all the Game set-up requirements together to run the various Game Simulations.
Transparent declaration of options.
Uses gateways/adapters to build configuration.
 */
public class RunGameSimulations {

    public void runAllGameSimulations(){

        System.out.println("Running: Basic 2-players, 2-dice, small board game. Player hits allowed. End does note need to be exact.");

        // Transparent declaration: "this is what I want"
        PlayerOption playersOpt = PlayerOption.TWO;
        DiceOption diceOpt = DiceOption.TWO;
        BoardOption boardOpt = BoardOption.SMALL;
        EndOption endOpt = EndOption.OVERSHOOT_ALLOWED;
        HitOption hitOpt = HitOption.ALLOW;

        // Gateway dispatch
        Player[] players = PlayerFactoryGateway.createPlayers(playersOpt);
        GameBoard board = BoardFactoryGateway.createBoard(boardOpt);
        DiceShaker dice = DiceFactoryGateway.createDice(diceOpt);
        EndStrategy endStrategy = EndFactoryGateway.createEndStrategy(endOpt);
        HitStrategy hitStrategy = HitFactoryGateway.createHitStrategy(hitOpt);
        List<GameListener> listeners = List.of(new ObserverConsoleLogger());

        // Single configuration passed to engine
        GameConfiguration config = new GameConfiguration(players, board, dice, endStrategy, hitStrategy, listeners);

        GameEngine engine = new GameEngine(config);
        engine.playGame();


        GameSimulationFactory factory = new BasicTwoPlayerSimulation();
        GameConfiguration config = factory.createConfiguration();

        GameEngine engine = new GameEngine(config);

        engine.playGame();
    }
}
