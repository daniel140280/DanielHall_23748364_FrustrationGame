//package gamesimulationsetup;
//
//import board.BoardEnums;
//import board.GameBoard;
//import boardfactory.GameBoardFactory;
//import dice.DiceEnums;
//import dice.DiceShaker;
//import dice.RandomDoubleDiceShaker;
//import gameobserver.GameListener;
//import gameobserver.ObserverConsoleLogger;
//import gamestrategies.EndStrategy;
//import gamestrategies.HitStrategy;
//import gamestrategies.RuleSet;
//import gamestrategies.endimplementations.ExactEndStrategy;
//import gamestrategies.endimplementations.OvershootAllowedStrategy;
//import gamestrategies.hitimplementations.AllowHitStrategy;
//import gamestrategies.hitimplementations.ForfeitOnHitStrategy;
//import players.BluePlayer;
//import players.Player;
//import players.PlayerEnums;
//import players.RedPlayer;
//import rungame.GameConfiguration;
//
//import java.util.List;
//
//public class BasicTwoPlayerSimulation implements GameSimulationFactory {
//    @Override
//    public GameConfiguration createConfiguration() {
//        Player[] players = new Player[]{new RedPlayer(), new BluePlayer()};
//        //GameBoard board = GameBoardFactory.createBoard(BoardEnums.SMALL);
//        DiceShaker dice = new RandomDoubleDiceShaker();
//        RuleSet rules = new RuleSet(false, true); // not exact end, hit allowed
//        List<GameListener> listeners = List.of(new ObserverConsoleLogger());
//
//        HitStrategy hitStrategy = rules.allowsPlayerHit() ? new AllowHitStrategy() : new ForfeitOnHitStrategy();
//        EndStrategy endStrategy = rules.requireExactEnd() ? new ExactEndStrategy() : new OvershootAllowedStrategy();
//
//        return new GameConfiguration(players, board, dice, hitStrategy, endStrategy, listeners);
//    }
//
//
//}
