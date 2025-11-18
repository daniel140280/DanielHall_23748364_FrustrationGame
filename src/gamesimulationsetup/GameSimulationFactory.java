package gamesimulationsetup;

import board.GameBoard;
import dice.DiceShaker;
import gameobserver.GameListener;
import gamestrategies.RuleSet;
import players.Player;
import players.PlayerEnums;
import board.BoardEnums;
import dice.DiceEnums;

import java.util.List;


//Factory interface utilised as a contract for all game simulations to adhere to.
public interface GameSimulationFactory {
    //Setting up the game requires players, a gameboard, number of dice and the relevant tracking system (listeners). Game rules are applied against the player moves.
    Player[] createPlayers(PlayerEnums playerType);
    GameBoard createBoard(BoardEnums boardSize);
    DiceShaker createDice(DiceEnums diceType);
    RuleSet defineRules();
    List<GameListener> setupListeners();


}
