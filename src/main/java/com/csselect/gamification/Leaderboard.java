package com.csselect.gamification;

import com.csselect.inject.Injector;
import com.csselect.database.DatabaseAdapter;
import com.csselect.user.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Represents a leaderboard and consists of a list of players.
 * This class is realised as a singleton.
 */
public final class Leaderboard {

    private final DatabaseAdapter databaseAdapter;
    private List<Player> players;
    private LeaderboardSortingStrategy strategy;
    private static Leaderboard instance;

    private Leaderboard() {
        this.players = new LinkedList<>();
        databaseAdapter =  Injector.getInstance().getDatabaseAdapter();
        setSortingStrategy(new SortScoreLastWeek());
    }

    /**
     * Gets the {@link Leaderboard}.
     * @return Instance of the Leaderboard
     */
    public static Leaderboard getInstance() {
        if (Leaderboard.instance == null) {
            Leaderboard.instance = new Leaderboard();
        }
        return Leaderboard.instance;
    }

    /**
     * Gets the current sorting strategy.
     * @return The current sorting strategy.
     */
    public LeaderboardSortingStrategy getStrategy() {
        return strategy;
    }

    /**
     * Sets the strategy that is supposed to be used to sort the list of players.
     * @param strategy The strategy to use.
     */
    public void setSortingStrategy(LeaderboardSortingStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Gets the sorted list of players.
     * @return The sorted list of players.
     */
    public Map<Player, Integer> getPlayers() {
        players = getPlayersFromDatabase();
        return strategy.sort(players);
    }

    /**
     * Gets the current list of all players from the database.
     * @return The list of players.
     */
    private List<Player> getPlayersFromDatabase() {
        return new LinkedList<>(databaseAdapter.getPlayers());
    }

}
