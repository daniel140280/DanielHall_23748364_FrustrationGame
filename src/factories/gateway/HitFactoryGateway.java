package factories.gateway;

import factories.HitFactory;
import factories.adapters.AllowHitFactoryAdapter;
import factories.adapters.ForbidHitFactoryAdapter;
import gameconfig.HitOption;
import gamestrategies.HitStrategy;

import java.util.HashMap;
import java.util.Map;
/**
 * Gateway for HitStrategy creation.
 * Uses a registry of adapters keyed by HitOption.
 */

public class HitFactoryGateway {
    private static final Map<HitOption, HitFactory> hitRegistry = new HashMap<>();

    static {
        hitRegistry.put(HitOption.ALLOW, new AllowHitFactoryAdapter());
        hitRegistry.put(HitOption.FORBID, new ForbidHitFactoryAdapter());
    }

    public static HitStrategy createHitStrategy(HitOption option) {
        HitFactory factory = hitRegistry.get(option);
        if (factory == null){
            throw new IllegalArgumentException("Unsupported HitOption: " + option);
        }
        return factory.createHitStrategy(option);
    }

}
