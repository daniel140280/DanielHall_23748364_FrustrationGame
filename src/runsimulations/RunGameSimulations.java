package runsimulations;

import gameconfig.*;
import gameobserver.GameListener;
import gameobserver.ObserverConsoleLogger;
import gamesimulationsetup.GameSimulationFactory;

import rungame.GameConfiguration;
import rungame.GameEngine;

import java.util.List;

/*
The method is a facade factory method, bringing all the Game set-up requirements together to run the various Game Simulations.
Transparent declaration of options.
Uses gateways/adapters to build configuration.
 *      Loops through all combinations of simulation parameters.
 *      Transparent declaration of each simulation run.
 */
public class RunGameSimulations {

    public void runAllGameSimulations(){

        List<GameListener> listeners = List.of(new ObserverConsoleLogger());

        // Loop through all game variations.
        for(PlayerOption playerOption : PlayerOption.values()){
            for(DiceOption diceOption : DiceOption.values()){
                for(BoardOption boardOption : BoardOption.values()){
                    for(HitOption hitOption : HitOption.values()){
                        for(EndOption endOption : EndOption.values()){

                            //Print on the simulation scenario.
                            System.out.println("--------------Running simulation--------------\n");
                            System.out.printf("Game consists of %s players, %s dice, a %s board, the Hit strategy is %s and %s End strategy is played", playerOption, diceOption, boardOption, hitOption, endOption);
                            System.out.println(); //Blank line added for improved visibility.

                            //Game simulations now constructed directly through Game set-up enums.
                            GameEngine engine = new GameEngine(playerOption, diceOption, boardOption, hitOption, endOption, listeners);

                            //Run the game.
                            engine.playGame();
                        }
                    }
                }
            }
        }
    }
}


// Single configuration passed to engine
//GameConfiguration config = new GameConfiguration(players, board, dice, endStrategy, hitStrategy, listeners);
//GameEngine engine = new GameEngine(config);
//        engine.playGame();
//GameSimulationFactory factory = new BasicTwoPlayerSimulation();
//GameConfiguration config = factory.createConfiguration();
//GameEngine engine = new GameEngine(config);
//        engine.playGame();