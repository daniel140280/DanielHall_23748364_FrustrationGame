package factories.adapters;

import factories.HitFactory;
import gameconfig.HitOption;
import gamestrategies.HitStrategy;
import gamestrategies.hitimplementations.AllowHitStrategy;

public class AllowHitFactoryAdapter implements HitFactory {
    @Override
    public HitStrategy createHitStrategy(HitOption option) {
        return new AllowHitStrategy();
    }

}
