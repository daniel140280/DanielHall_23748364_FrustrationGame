package factories.adapters;

import board.GameBoard;
import gameconfig.PlayerOption;
import factories.PlayerFactory;
import players.*;

public class FourPlayerFactoryAdapter implements PlayerFactory {
    @Override
    public Player[] createPlayers(PlayerOption option) {
        return new Player[]{ new RedPlayer(), new BluePlayer(), new GreenPlayer(), new YellowPlayer() };
    }
}
