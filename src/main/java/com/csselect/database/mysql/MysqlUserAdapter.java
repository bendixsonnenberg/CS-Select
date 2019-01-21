package com.csselect.database.mysql;

import com.csselect.Injector;
import com.csselect.database.DatabaseAdapter;
import com.csselect.database.UserAdapter;
import com.csselect.game.Game;
import org.intellij.lang.annotations.Language;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Mysql-Implementation of the {@link UserAdapter} Interface
 */
public abstract class MysqlUserAdapter implements UserAdapter {

    private final int id;
    private static final MysqlDatabaseAdapter DATABASE_ADAPTER = (MysqlDatabaseAdapter) Injector.getInjector()
            .getInstance(DatabaseAdapter.class);

    /**
     * Creates a new {@link MysqlUserAdapter} with the given id
     * @param id id of the adapter
     */
    MysqlUserAdapter(int id) {
        this.id = id;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String getEmail() {
        return getString("email");
    }

    @Override
    public String getPasswordHash() {
        return getString("hash");
    }

    @Override
    public String getPasswordSalt() {
        return getString("salt");
    }

    @Override
    public String getLanguage() {
        return getString("language");
    }

    @Override
    public void setEmail(String email) {
        setString("email", email);
    }

    @Override
    public void setPassword(String hash, String salt) {
        try {
            DATABASE_ADAPTER.executeMysqlUpdate("UPDATE " + getTableName()
                    + " SET hash='" + hash + "', salt='" + salt + "' WHERE (id='" + id + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setLanguage(String langCode) {
        setString("language", langCode);
    }

    @Override
    public Collection<Game> getActiveGames() {
        return null;
    }

    @Override
    public Collection<Game> getTerminatedGames() {
        return null;
    }

    private ResultSet getRow() throws SQLException {
        return DATABASE_ADAPTER.executeMysqlQuery("SELECT * FROM " + getTableName() + " WHERE (ID='" + id + "');");
    }

    /**
     * Gets a String from the given columnlabel
     * @param columnLabel columnlabel to get the string from
     * @return retrieved string
     */
    String getString(String columnLabel) {
        try {
            ResultSet resultSet = getRow();
            resultSet.next();
            String res = resultSet.getString(columnLabel);
            resultSet.close();
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Sets a String on the given columnlabel
     * @param columnLabel columnlabel to set the String on
     * @param value string to set
     */
    void setString(String columnLabel, String value) {
        try {
            @Language("sql")
            String query = "UPDATE " + getTableName() + " SET " + columnLabel + " = '" + value
                    + "' WHERE (id='" + id + "');";
            DATABASE_ADAPTER.executeMysqlUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the table name used by the queries in the adapter
     * @return tablename
     */
    abstract String getTableName();
}
