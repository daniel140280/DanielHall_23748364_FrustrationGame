package factories.adapters;

import board.GameBoard;
import factories.EndFactory;
import gameconfig.EndOption;
import gamestrategies.EndStrategy;
import gamestrategies.endimplementations.ExactEndStrategy;

public class ExactEndFactoryAdapter implements EndFactory {
    @Override
    public EndStrategy createEndStrategy(GameBoard board, EndOption option) {
        return new ExactEndStrategy(board);         //inject board (BEAN?!?)
    }
}

