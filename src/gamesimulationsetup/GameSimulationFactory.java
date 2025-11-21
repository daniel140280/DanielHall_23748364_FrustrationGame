package gamesimulationsetup;

import rungame.GameConfiguration;

import java.util.List;


//Factory interface utilised as a contract for all game simulations to adhere to.
public interface GameSimulationFactory {
    GameConfiguration createConfiguration();

}
