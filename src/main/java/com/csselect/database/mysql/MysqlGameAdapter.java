package com.csselect.database.mysql;

import com.csselect.Injector;
import com.csselect.database.DatabaseAdapter;
import com.csselect.database.GameAdapter;
import com.csselect.game.Feature;
import com.csselect.game.FeatureSet;
import com.csselect.game.Gamemode;
import com.csselect.game.Round;
import com.csselect.game.Termination;
import com.csselect.user.Organiser;
import com.csselect.user.Player;
import com.csselect.utils.FeatureSetUtils;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.StringJoiner;

/**
 * Mysql-Implementation of the {@link GameAdapter} Interface
 */
public class MysqlGameAdapter extends MysqlAdapter implements GameAdapter {

    private static final MysqlDatabaseAdapter DATABASE_ADAPTER
            = (MysqlDatabaseAdapter) Injector.getInjector().getInstance(DatabaseAdapter.class);

    private String databaseName;
    private String title;
    private String description;

    /**
     * Creates a new {@link MysqlGameAdapter} with the given id
     * @param id adapters id
     */
    MysqlGameAdapter(int id) {
        super(id);
    }

    /**
     * Creates a new {@link MysqlGameAdapter} with the next available id
     * @throws SQLException Thrown if an error occurs while communicating with the database
     */
    MysqlGameAdapter() throws SQLException {
        super(DATABASE_ADAPTER.getNextGameID());
        DATABASE_ADAPTER.executeMysqlUpdate("INSERT INTO games () VALUES ();");
    }

    @Override
    public String getTitle() {
        if (title == null) {
            title = getString("title");
        }
        return title;

    }

    @Override
    public String getDescription() {
        if (description == null) {
            description = getString("description");
        }
        return description;
    }

    @Override
    public String getDatabaseName() {
        if (databaseName != null) {
            return databaseName;
        } else {
            databaseName = getString("databaseName");
            return databaseName;
        }
    }

    @Override
    public FeatureSet getFeatures() {
        try {
            return FeatureSetUtils.loadFeatureSet("dataSetName");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int getNumberOfRounds() {
        ResultSet set;
        try {
            set = DATABASE_ADAPTER.executeMysqlQuery("SELECT MAX(id) AS id FROM rounds;", getDatabaseName());
            if (!set.next()) {
                set.close();
                return 0;
            } else {
                int tmp = set.getInt("id");
                set.close();
                return tmp;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Collection<String> getInvitedPlayers() {
        Collection<String> emails = new HashSet<>();
        try {
            ResultSet resultSet = DATABASE_ADAPTER
                    .executeMysqlQuery("SELECT email FROM players WHERE invited=1", databaseName);
            while (resultSet.next()) {
                emails.add(resultSet.getString("email"));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emails;
    }

    @Override
    public Collection<Player> getPlayingPlayers() {
        Collection<Player> players = new HashSet<>();
        try {
            ResultSet resultSet = DATABASE_ADAPTER
                    .executeMysqlQuery("SELECT email FROM players WHERE invited=0", databaseName);
            while (resultSet.next()) {
                players.add(DATABASE_ADAPTER.getPlayer(resultSet.getString("email")));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    @Override
    public Collection<Round> getRounds() {
        Collection<Round> rounds = new HashSet<>();
        try {
            ResultSet set = DATABASE_ADAPTER.executeMysqlQuery("SELECT * FROM rounds", databaseName);
            while (set.next()) {
                rounds.add(getGamemode().createRound(new Player(new MysqlPlayerAdapter(
                        set.getInt("player_id")))));
            }
            set.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rounds;
    }

    @Override
    public Gamemode getGamemode() {
        return parseGamemode(getString("gamemode"));
    }

    @Override
    public Termination getTermination() {
        return parseTermination(getString("termination"));
    }

    @Override
    public Organiser getOrganiser() {
        return new Organiser(new MysqlOrganiserAdapter(getInt("organiserId")));
    }

    @Override
    public void setTermination(Termination termination) {
        setString("termination", termination.toString());
    }

    @Override
    public void setFeatures(FeatureSet featureSet) {
        setString("dataset", featureSet.getIdentifier());
    }

    @Override
    public void setFinished() {
        setBoolean("isTerminated", true);
    }

    @Override
    public void setTitle(String title) {
        setString("title", title);
    }

    @Override
    public void setDescription(String description) {
        setString("description", description);
    }

    @Override
    public void setDatabase(String name) {
        databaseName = name;
        setString("databaseName", name);
        try {
            createPlayersTable();
            createRoundsTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setGamemode(Gamemode gamemode) {
        setString("gamemode", gamemode.toString());
    }

    @Override
    public void setOrganiser(Organiser organiser) {
        setInt("organiserId", organiser.getId());
    }

    @Override
    public boolean isFinished() {
        return getBoolean("isTerminated");
    }

    @Override
    public void addRound(Round round) {
        try {
            DATABASE_ADAPTER.executeMysqlUpdate("INSERT INTO rounds ("
                    + "playerid,'time',quality,points,uselessFeatures,chosenFeatures,shownFeatures)"
                    + "VALUES (?,NOW(),?,?,?,?,?);", databaseName, new IntParam(round.getPlayer().getId()),
                    new DoubleParam(round.getQuality()), new IntParam(round.getPoints()),
                    new StringParam(featuresToString(round.getUselessFeatures())),
                    new StringParam(featuresToString(round.getChosenFeatures())),
                    new StringParam(featuresToString(round.getShownFeatures())));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addInvitedPlayers(Collection<String> emails) {
        StringJoiner joiner = new StringJoiner(",");
        Param[] params = new Param[emails.size()];
        int i = 0;
        for (String email : emails) {
            joiner.add("(?,1)");
            params[i] = new StringParam(email);
            i++;
        }
        String values = joiner.toString();
        try {
            DATABASE_ADAPTER.executeMysqlUpdate("INSERT INTO players (email,invited) VALUES " + values
                    + " ON DUPLICATE KEY UPDATE email=email;", databaseName, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPlayingPlayers(Collection<String> emails) {
        StringJoiner joiner = new StringJoiner(",");
        Param[] params = new Param[emails.size()];
        int i = 0;
        for (String email : emails) {
            joiner.add("(?,0)");
            params[i] = new StringParam(email);
            i++;
        }
        String values = joiner.toString();
        try {
            DATABASE_ADAPTER.executeMysqlUpdate(
                    "REPLACE INTO players (email,invited) VALUES " + values + ";", databaseName, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPlayingPlayer(int id) {
        StringParam email = new StringParam(DATABASE_ADAPTER.getPlayerAdapter(id).getEmail());
        try {
            DATABASE_ADAPTER.executeMysqlUpdate(
                            "REPLACE INTO players (email,invited) VALUES (?,0);", databaseName, email);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeInvitedPlayers(Collection<String> emails) {
        emails.forEach(e -> {
            try {
                DATABASE_ADAPTER.executeMysqlUpdate(
                        "DELETE FROM players WHERE email=? AND invited=1;", databaseName, new StringParam(e));
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
    }

    @Override
    public boolean checkDuplicateFeatureProvision(Collection<Feature> features) {
        try {
            ResultSet set = DATABASE_ADAPTER.executeMysqlQuery("SELECT id FROM rounds WHERE shownFeatures=?",
                    new StringParam(featuresToString(features)));
            return set.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    ResultSet getRow() throws SQLException {
        return DATABASE_ADAPTER.executeMysqlQuery("SELECT * FROM games WHERE (id=" + getID() + ");");
    }

    @Override
    String getTableName() {
        return "games";
    }

    private void createRoundsTable() throws SQLException {
        DATABASE_ADAPTER.executeMysqlUpdate(Query.CREATE_ROUNDS_TABLE, getDatabaseName());
    }

    private void createPlayersTable() throws SQLException {
        DATABASE_ADAPTER.executeMysqlUpdate(Query.CREATE_GAMES_PLAYERS_TABLE, getDatabaseName());
    }

    private String featuresToString(Collection<Feature> features) {
        StringJoiner joiner = new StringJoiner(",");
        features.forEach(f -> joiner.add("" + f.getID()));
        return joiner.toString();
    }
}
