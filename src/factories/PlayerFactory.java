package factories;

import board.GameBoard;
import gameconfig.PlayerOption;
import players.Player;
/*
Player factory abstraction utilises the configuration enums to determine the concrete instantiation.
 */
public interface PlayerFactory {
    Player[] createPlayers(PlayerOption option);        // TWO or FOUR player
}
