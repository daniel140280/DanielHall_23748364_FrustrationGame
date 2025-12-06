package test.mocks;

import gameobserver.GameListener;
import players.Player;
import playersgamepositions.PlayersInGameContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Mock listener for testing game events.
 * Captures all events for verification in tests.
 */
public class MockGameListener implements GameListener {
    private final List<MoveEvent> successfulMoves = new ArrayList<>();
    private final List<MoveEvent> blockedMoves = new ArrayList<>();
    private final List<EndEvent> endReachedEvents = new ArrayList<>();
    private final List<EndEvent> endForfeitEvents = new ArrayList<>(); // REQUIRED CHANGE: New list for forfeits
    private boolean gameOverCalled = false;

    @Override
    public void onSuccessfulMove(Player player, PlayersInGameContext context,
                                 int fromPosition, int toPosition, int roll) {
        successfulMoves.add(new MoveEvent(player, fromPosition, toPosition, roll));
    }

    @Override
    public void onBlockedMove(Player player, PlayersInGameContext context,
                              int attemptedPosition, int roll) {
        blockedMoves.add(new MoveEvent(player, context.getPlayersPosition().getBoardIndex(),
                attemptedPosition, roll));
    }

    @Override
    public void onEndReached(Player player, PlayersInGameContext context,
                             int attemptedPosition, int overshoot, int roll) {
        endReachedEvents.add(new EndEvent(player, attemptedPosition, overshoot, roll));
    }
    @Override
    public void onEndForfeit(Player player, PlayersInGameContext context,
                             int attemptedPosition, int overshoot, int roll) {
        // AttemptedPosition is the index they would have landed on if allowed
        endForfeitEvents.add(new EndEvent(player, attemptedPosition, overshoot, roll));
    }
    @Override
    public void onGameOver(Player[] players, Map<Player, PlayersInGameContext> contexts) {
        gameOverCalled = true;
    }

    // Verification methods
    public int getSuccessfulMoveCount() { return successfulMoves.size(); }
    public int getBlockedMoveCount() { return blockedMoves.size(); }
    public int getEndEventCount() { return endReachedEvents.size(); }
    public int getEndForfeitCount() { return endForfeitEvents.size(); }
    public boolean wasGameOverCalled() { return gameOverCalled; }

    public List<MoveEvent> getSuccessfulMoves() { return successfulMoves; }
    public List<MoveEvent> getBlockedMoves() { return blockedMoves; }
    public List<EndEvent> getEndReachedEvents() { return endReachedEvents; }
    public List<EndEvent> getEndForfeitEvents() { return endForfeitEvents; }

    public MoveEvent getLastSuccessfulMove() {
        return successfulMoves.isEmpty() ? null : successfulMoves.get(successfulMoves.size() - 1);
    }

    // Helper classes
    public static class MoveEvent {
        public final Player player;
        public final int fromPosition;
        public final int toPosition;
        public final int roll;

        public MoveEvent(Player player, int fromPosition, int toPosition, int roll) {
            this.player = player;
            this.fromPosition = fromPosition;
            this.toPosition = toPosition;
            this.roll = roll;
        }
    }

    public static class EndEvent {
        public final Player player;
        public final int attemptedPosition;
        public final int overshoot;
        public final int roll;

        public EndEvent(Player player, int attemptedPosition, int overshoot, int roll) {
            this.player = player;
            this.attemptedPosition = attemptedPosition;
            this.overshoot = overshoot;
            this.roll = roll;
        }
    }
}
