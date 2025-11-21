package factories.adapters;

import gameconfig.PlayerOption;
import factories.PlayerFactory;
import players.BluePlayer;
import players.Player;
import players.RedPlayer;

public class FourPlayerFactoryAdapter implements PlayerFactory {
    @Override
    public Player[] createPlayers(PlayerOption option) {
        return new Player[]{ new RedPlayer(), new BluePlayer()};
        //, new GreenPlayer(), new YellowPlayer() };
    }

}
