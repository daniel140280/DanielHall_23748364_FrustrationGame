package factories.adapters;

import factories.EndFactory;
import gameconfig.EndOption;
import gamestrategies.EndStrategy;
import gamestrategies.endimplementations.OvershootAllowedStrategy;

public class OvershootEndFactoryAdapter implements EndFactory {
    @Override
    public EndStrategy createEndStrategy(EndOption option) {
        return new OvershootAllowedStrategy();
    }

}
