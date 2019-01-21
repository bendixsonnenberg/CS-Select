package com.csselect.database.mysql;

import com.csselect.Injector;
import com.csselect.database.DatabaseAdapter;
import com.csselect.database.PlayerAdapter;
import com.csselect.game.Game;
import com.csselect.game.Round;
import com.csselect.gamification.PlayerStats;

import java.util.Collection;

/**
 * Mysql-Implementation of the {@link PlayerAdapter} Interface
 */
public class MysqlPlayerAdapter extends MysqlUserAdapter implements PlayerAdapter {

    private static final MysqlDatabaseAdapter DATABASE_ADAPTER
            = (MysqlDatabaseAdapter) Injector.getInjector().getInstance(DatabaseAdapter.class);


    /**
     * Creates a new {@link MysqlUserAdapter} with the given id
     *
     * @param id id of the adapter
     */
    public MysqlPlayerAdapter(int id) {
        super(id);
    }

    @Override
    public String getUsername() {
        return getString("username");
    }

    /**
     * Sets {@link com.csselect.user.Player}s username
     * As usernames should be unique this method is only available in package to prevent foreign classes from calling it
     *
     * @param username username to set
     */
    void setUsername(String username) {
        setString("username", username);
    }

    @Override
    public PlayerStats getPlayerStats() {
        return null;
    }

    @Override
    public Collection<Game> getInvitedGames() {
        return null;
    }

    @Override
    public Collection<Round> getRounds() {
        return null;
    }

    @Override
    String getTableName() {
        return "PLAYERS";
    }
}
