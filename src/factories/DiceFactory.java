package factories;

import gameconfig.DiceOption;
import dice.DiceShaker;
/*
Dice factory abstraction utilises the configuration enums to determine the concrete instantiation.
 */
public interface DiceFactory {
    DiceShaker createDice(DiceOption option);           // ONE or TWO dice
}
