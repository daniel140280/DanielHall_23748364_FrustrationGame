package factories.adapters;

import board.GameBoard;
import factories.EndFactory;
import gameconfig.EndOption;
import gamestrategies.EndStrategy;
import gamestrategies.endimplementations.OvershootAllowedStrategy;

public class OvershootEndFactoryAdapter implements EndFactory {
    @Override
    public EndStrategy createEndStrategy(GameBoard board, EndOption option) {
        return new OvershootAllowedStrategy(board);     //inject board (BEAN?!)
    }

}
