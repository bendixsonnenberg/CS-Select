package com.csselect.database.mysql;

import com.csselect.inject.Injector;
import com.csselect.database.OrganiserAdapter;
import com.csselect.game.Game;
import com.csselect.game.gamecreation.patterns.GameOptions;
import com.csselect.game.gamecreation.patterns.Pattern;
import com.csselect.parser.GamemodeParser;
import com.csselect.parser.TerminationParser;
import org.pmw.tinylog.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.StringJoiner;

/**
 * Mysql-Implementation of the {@link OrganiserAdapter} Interface
 */
public class MysqlOrganiserAdapter extends MysqlUserAdapter implements OrganiserAdapter {

    private static final MysqlDatabaseAdapter DATABASE_ADAPTER
            = (MysqlDatabaseAdapter) Injector.getInstance().getDatabaseAdapter();

    /**
     * Creates a new {@link MysqlOrganiserAdapter} with the given id
     *
     * @param id id of the adapter
     */
    MysqlOrganiserAdapter(int id) {
        super(id);
    }

    /**
     * Creates a new {@link MysqlDatabaseAdapter} with the next available id
     * @param email organisers email
     * @param hash organisers password hash
     * @param salt organisers password salt
     * @throws SQLException Thrown if an error occurs while communicating with the database server
     */
    MysqlOrganiserAdapter(String email, String hash, String salt) throws SQLException {
        super(DATABASE_ADAPTER.getNextIdOfTable(TableNames.ORGANISERS));
        DATABASE_ADAPTER.executeMysqlUpdate("INSERT INTO " + TableNames.ORGANISERS + " (email,hash,salt,language) "
                        + "VALUES (?,?,?,?);", new StringParam(email), new StringParam(hash), new StringParam(salt),
                        new StringParam(DEFAULT_LANGUAGE));
    }

    @Override
    public Collection<Pattern> getPatterns() {
        Collection<Pattern> patterns = new HashSet<>();
        try {
            ResultSet set = DATABASE_ADAPTER.executeMysqlQuery(
                    "SELECT * FROM " + TableNames.PATTERNS + " WHERE organiserId=?;", new IntParam(getID()));
            while (set.next()) {
                GameOptions options = new GameOptions();
                options.setTitle(set.getString("gameTitle"));
                options.setDescription(set.getString("description"));
                options.setDataset(set.getString("dataset"));
                options.setResultDatabaseName(set.getString("databasename"));
                options.setTermination(TerminationParser.parseTermination(set.getString("termination")));
                options.setGamemode(GamemodeParser.parseGamemode(set.getString("gamemode")));
                options.addInvitedEmails(emailCollectionFromString(set.getString("invitedPlayers")));
                patterns.add(new Pattern(options, set.getString("title")));
            }
        } catch (SQLException e) {
            Logger.error(e);
        }
        return patterns;
    }

    @Override
    public void addPattern(Pattern pattern) {
        GameOptions gameOptions = pattern.getGameOptions();
        StringJoiner joiner = new StringJoiner(",");
        gameOptions.getInvitedEmails().forEach(joiner::add);
        String emails = joiner.toString();
        try {
            DATABASE_ADAPTER.executeMysqlUpdate("REPLACE INTO " + TableNames.PATTERNS
                    + " (organiserId,title,gameTitle,description,dataset,databasename,termination,gamemode,"
                            + "invitedPlayers) VALUES (?,?,?,?,?,?,?,?,?)", new IntParam(getID()),
                    new StringParam(pattern.getTitle()), new StringParam(gameOptions.getTitle()),
                    new StringParam(gameOptions.getDescription()), new StringParam(gameOptions.getDataset()),
                    new StringParam(gameOptions.getResultDatabaseName()),
                    new StringParam(gameOptions.getTermination().toString()),
                    new StringParam(gameOptions.getGamemode().toString()), new StringParam(emails));
        } catch (SQLException e) {
            Logger.error(e);
        }
    }

    @Override
    public boolean gameTitleInUse(String title) {
        try {
            ResultSet set = DATABASE_ADAPTER.executeMysqlQuery("SELECT * FROM games WHERE title=? AND organiserId=?;",
                    new StringParam(title), new IntParam(this.getID()));
            return set.next();
        } catch (SQLException e) {
            Logger.error(e);
            return true;
        }
    }

    @Override
    String getTableName() {
        return TableNames.ORGANISERS;
    }

    @Override
    public Collection<Game> getActiveGames() {
        return DATABASE_ADAPTER.getActiveGames(this);
    }

    @Override
    public Collection<Game> getTerminatedGames() {
        return DATABASE_ADAPTER.getTerminatedGames(this);
    }

    private Collection<String> emailCollectionFromString(String emails) {
        Collection<String> emailCollection = new HashSet<>();
        Collections.addAll(emailCollection, emails.split(","));
        return emailCollection;
    }
}
