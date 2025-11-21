package factories.adapters;

import gameconfig.DiceOption;
import dice.DiceShaker;
import dice.RandomDoubleDiceShaker;
import factories.DiceFactory;

public class TwoDiceFactoryAdapter implements DiceFactory {
    @Override
    public DiceShaker createDice(DiceOption option) {
        return new RandomDoubleDiceShaker();
    }

}
