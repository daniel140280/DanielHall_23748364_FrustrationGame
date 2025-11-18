package gamesimulationsetup;

import board.GameBoard;
import dice.DiceShaker;
import players.Player;

//Factory interface utilised as a contract for all game simulations to adhere to.
public interface GameSimulationFactory {
    //Setting up the game requires players, a gameboard, number of dice and the relevant tracking system (listeners). Game rules are applied against the player moves.
    Player[] createPlayers();
    GameBoard createBoard();
    DiceShaker createDice();
   // List<T> createListeners();

}
