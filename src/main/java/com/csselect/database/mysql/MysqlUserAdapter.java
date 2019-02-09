package com.csselect.database.mysql;

import com.csselect.inject.Injector;
import com.csselect.database.UserAdapter;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mysql-Implementation of the {@link UserAdapter} Interface
 */
public abstract class MysqlUserAdapter extends MysqlAdapter implements UserAdapter {

    private static final MysqlDatabaseAdapter DATABASE_ADAPTER = (MysqlDatabaseAdapter) Injector.getInstance()
            .getDatabaseAdapter();

    static final String DEFAULT_LANGUAGE = "en";

    /**
     * Creates a new {@link MysqlUserAdapter} with the given id
     * @param id id of the adapter
     */
    MysqlUserAdapter(int id) {
        super(id);
    }

    @Override
    public final String getEmail() {
        return getString("email");
    }

    @Override
    public final String getPasswordHash() {
        return getString("hash");
    }

    @Override
    public final String getPasswordSalt() {
        return getString("salt");
    }

    @Override
    public final String getLanguage() {
        return getString("language");
    }

    @Override
    public final void setEmail(String email) {
        setString("email", email);
    }

    @Override
    public final void setPassword(String hash, String salt) {
        try {
            DATABASE_ADAPTER.executeMysqlUpdate("UPDATE " + getTableName()
                    + " SET hash='" + hash + "', salt='" + salt + "' WHERE (id='" + getID() + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public final void setLanguage(String langCode) {
        setString("language", langCode);
    }

    @Override
    final ResultSet getRow() throws SQLException {
        return DATABASE_ADAPTER.executeMysqlQuery("SELECT * FROM " + getTableName() + " WHERE (ID='" + getID() + "');");
    }
}
