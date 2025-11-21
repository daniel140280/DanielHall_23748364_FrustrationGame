package factories.gateway;

import gameconfig.PlayerOption;
import factories.PlayerFactory;
import factories.adapters.FourPlayerFactoryAdapter;
import factories.adapters.TwoPlayerFactoryAdapter;
import players.Player;

import java.util.HashMap;
import java.util.Map;

/*
Gateway + Adapter Implementations
Instead of if statements, each factory option has its own adapter.
The gateway dispatches to the right adapter.
 */
public class PlayerFactoryGateway {
    private static final Map<PlayerOption, PlayerFactory> registry = new HashMap<>();

    static {
        registry.put(PlayerOption.TWO, new TwoPlayerFactoryAdapter());
        registry.put(PlayerOption.FOUR, new FourPlayerFactoryAdapter());
    }

    public static Player[] createPlayers(PlayerOption option) {
        PlayerFactory factory = registry.get(option);
        if (factory == null){
            throw new IllegalArgumentException("Unsupported PlayerOption: " + option);
        }
        return factory.createPlayers(option);
    }
}



