package factories;

import gameconfig.EndOption;
import gamestrategies.EndStrategy;

/*
Strategy factory abstraction utilises the configuration enums to determine the concrete instantiation.
 */
public interface EndFactory {
    EndStrategy createEndStrategy(EndOption option);   // EXACT or OVERSHOOT_ALLOWED

}