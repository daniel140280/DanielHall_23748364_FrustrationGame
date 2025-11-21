package factories.gateway;

import factories.EndFactory;
import factories.adapters.ExactEndFactoryAdapter;
import factories.adapters.OvershootEndFactoryAdapter;
import gameconfig.EndOption;
import gamestrategies.EndStrategy;

import java.util.HashMap;
import java.util.Map;

public class EndFactoryGateway {
    private static final Map<EndOption, EndFactory> endRegistry = new HashMap<>();

    static {
        endRegistry.put(EndOption.EXACT, new ExactEndFactoryAdapter());
        endRegistry.put(EndOption.OVERSHOOT_ALLOWED, new OvershootEndFactoryAdapter());
    }

    public static EndStrategy createEndStrategy(EndOption option) {
        EndFactory factory = endRegistry.get(option);
        if (factory == null){
            throw new IllegalArgumentException("Unsupported EndOption: " + option);
        }
        return factory.createEndStrategy(option);
    }

}
