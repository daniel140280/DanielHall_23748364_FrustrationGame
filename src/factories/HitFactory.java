package factories;

import gameconfig.HitOption;
import gamestrategies.HitStrategy;

/*
Strategy factory abstraction utilises the configuration enums to determine the concrete instantiation.
 */
public interface HitFactory {
    HitStrategy createHitStrategy(HitOption option);   // ALLOW or FORBID
}
