package factories.adapters;

import factories.EndFactory;
import gameconfig.EndOption;
import gamestrategies.EndStrategy;
import gamestrategies.endimplementations.ExactEndStrategy;

public class ExactEndFactoryAdapter implements EndFactory {
    @Override
    public EndStrategy createEndStrategy(EndOption option) {
        return new ExactEndStrategy();
    }
}

