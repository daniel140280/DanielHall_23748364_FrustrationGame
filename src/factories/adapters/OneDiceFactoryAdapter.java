package factories.adapters;

import dice.RandomSingleDiceShaker;
import factories.DiceFactory;
import gameconfig.DiceOption;
import dice.DiceShaker;

public class OneDiceFactoryAdapter implements DiceFactory {
    @Override
    public DiceShaker createDice(DiceOption option) {
        return new RandomSingleDiceShaker();
    }
}
