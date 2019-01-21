package com.csselect.database.mysql;

import com.csselect.Injector;
import com.csselect.database.DatabaseAdapter;
import com.csselect.database.OrganiserAdapter;
import com.csselect.game.gamecreation.patterns.Pattern;

import java.util.Collection;

/**
 * Mysql-Implementation of the {@link OrganiserAdapter} Interface
 */
public class MysqlOrganiserAdapter extends MysqlUserAdapter implements OrganiserAdapter {

    private static final MysqlDatabaseAdapter DATABASE_ADAPTER
            = (MysqlDatabaseAdapter) Injector.getInjector().getInstance(DatabaseAdapter.class);
    /**
     * Creates a new {@link MysqlOrganiserAdapter} with the given id
     *
     * @param id id of the adapter
     */
    public MysqlOrganiserAdapter(int id) {
        super(id);
    }

    @Override
    public Collection<Pattern> getPatterns() {
        return null;
    }

    @Override
    public void addPattern(Pattern pattern) {

    }

    @Override
    String getTableName() {
        return "ORGANISERS";
    }
}