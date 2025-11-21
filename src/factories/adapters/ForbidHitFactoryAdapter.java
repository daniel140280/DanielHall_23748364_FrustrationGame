package factories.adapters;

import factories.HitFactory;
import gameconfig.HitOption;
import gamestrategies.HitStrategy;
import gamestrategies.hitimplementations.ForfeitOnHitStrategy;


public class ForbidHitFactoryAdapter implements HitFactory {
    @Override
    public HitStrategy createHitStrategy(HitOption option) {
        return new ForfeitOnHitStrategy();
    }
}
