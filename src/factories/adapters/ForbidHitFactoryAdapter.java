package factories.adapters;

import board.GameBoard;
import factories.HitFactory;
import gameconfig.HitOption;
import gamestrategies.HitStrategy;
import gamestrategies.hitimplementations.ForfeitOnHitStrategy;


public class ForbidHitFactoryAdapter implements HitFactory {
//    private final GameBoard board;

    public ForbidHitFactoryAdapter(){
    };
    @Override
    public HitStrategy createHitStrategy(HitOption option) {
        return new ForfeitOnHitStrategy();
    }
}
