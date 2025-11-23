package factories.adapters;


import board.GameBoard;
import gameconfig.PlayerOption;
import factories.PlayerFactory;
import players.BluePlayer;
import players.Player;
import players.RedPlayer;

// Adapter for TWO players
public class TwoPlayerFactoryAdapter implements PlayerFactory {
    @Override
    public Player[] createPlayers(PlayerOption option) {
        return new Player[]{ new RedPlayer(), new BluePlayer() };
    }
}