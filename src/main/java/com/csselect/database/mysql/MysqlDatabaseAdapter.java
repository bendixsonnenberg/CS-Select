package com.csselect.database.mysql;

import com.csselect.configuration.Configuration;
import com.csselect.database.DatabaseAdapter;
import com.csselect.database.GameAdapter;
import com.csselect.database.OrganiserAdapter;
import com.csselect.database.PlayerAdapter;
import com.csselect.game.Game;
import com.csselect.user.Organiser;
import com.csselect.user.Player;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Mysql-Implementation of the {@link DatabaseAdapter} Interface
 */
public class MysqlDatabaseAdapter implements DatabaseAdapter {

    private static final String PRODUCT_DATABASE_NAME = "CS_SELECT";

    private final String hostname;
    private final int port;
    private final String username;
    private final String password;
    private final Map<String, MysqlDataSource> dataSources;

    private final Map<Game, Organiser> gameMap;
    private final Map<Integer, GameAdapter> gameAdapterMap;

    /**
     * Creates a new MysqlDatabaseAdapter. Only to be used by the {@link com.csselect.Injector}
     * @param configuration configuration to use
     */
    public MysqlDatabaseAdapter(Configuration configuration) {
        gameMap = new HashMap<>();
        gameAdapterMap = new HashMap<>();
        this.hostname = configuration.getDatabaseHostname();
        this.port = configuration.getDatabasePort();
        this.username = configuration.getDatabaseUsername();
        this.password = configuration.getDatabasePassword();
        this.dataSources = new HashMap<>();
        this.dataSources.put(PRODUCT_DATABASE_NAME, createDataSource(PRODUCT_DATABASE_NAME));
        try {
            executeMysqlUpdate(Query.CREATE_PLAYER_TABLE);
            executeMysqlUpdate(Query.CREATE_ORGANISER_TABLE);
            executeMysqlUpdate(Query.CREATE_GAME_TABLE);
            executeMysqlUpdate(Query.CREATE_PATTERN_TABLE);
            executeMysqlUpdate(Query.CREATE_PLAYERSTATS_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public PlayerAdapter getPlayerAdapter(int id) {
        return new MysqlPlayerAdapter(id);
    }

    @Override
    public OrganiserAdapter getOrganiserAdapter(int id) {
        return new MysqlOrganiserAdapter(id);
    }

    @Override
    public GameAdapter getGameAdapter(int id) {
        if (gameAdapterMap.containsKey(id)) {
            return gameAdapterMap.get(id);
        } else if (id < getNextGameID()) {
            MysqlGameAdapter adapter = new MysqlGameAdapter(id);
            gameAdapterMap.put(id, adapter);
            return new MysqlGameAdapter(id);
        } else {
            try {
                MysqlGameAdapter adapter = new MysqlGameAdapter();
                gameAdapterMap.put(id, adapter);
                return adapter;
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public Player getPlayer(String email) {
        try {
            ResultSet set = executeMysqlQuery("SELECT * FROM players WHERE (email=?);", new StringParam(email));
            return getPlayer(set);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Player getPlayer(int id) {
        try {
            ResultSet set = executeMysqlQuery("SELECT * FROM players WHERE (id='" + id + "';");
            return getPlayer(set);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Player getPlayer(ResultSet set) throws SQLException {
        if (set.next()) {
            Player p = new Player(new MysqlPlayerAdapter(set.getInt("id")));
            set.close();
            return p;
        } else {
            set.close();
            return null;
        }
    }

    @Override
    public Organiser getOrganiser(String email) {
        try {
            ResultSet set = executeMysqlQuery("SELECT * FROM organisers WHERE (email=?);",
                    new StringParam(email));
            if (set.next()) {
                Organiser o = new Organiser(new MysqlOrganiserAdapter(set.getInt("id")));
                set.close();
                return o;
            } else {
                set.close();
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<Player> getPlayers() {
        Collection<Player> players = new HashSet<>();
        try {
            ResultSet set = executeMysqlQuery("SELECT * FROM players");
            while (set.next()) {
                players.add(new Player(new MysqlPlayerAdapter(set.getInt("id"))));
            }
            set.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    @Override
    public int getNextGameID() {
        try {
            return getNextIdOfTable("games");
        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
    }

    @Override
    public String getPlayerHash(int id) {
        try {
            ResultSet set = executeMysqlQuery("SELECT hash AS hash FROM players WHERE (id=" + id + ");");
            String hash;
            if (set.next()) {
                hash = set.getString("hash");
            } else {
                hash = null;
            }
            set.close();
            return hash;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getPlayerSalt(int id) {
        try {
            ResultSet set = executeMysqlQuery("SELECT salt AS salt FROM players WHERE (id=" + id + ");");
            String salt;
            if (set.next()) {
                salt = set.getString("salt");
            } else {
                salt = null;
            }
            set.close();
            return salt;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getOrganiserHash(int id) {
        try {
            ResultSet set = executeMysqlQuery("SELECT hash AS hash FROM organisers WHERE (id=" + id + ");");
            String hash;
            if (set.next()) {
                hash = set.getString("hash");
            } else {
                hash = null;
            }
            set.close();
            return hash;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getOrganiserSalt(int id) {
        try {
            ResultSet set = executeMysqlQuery("SELECT salt AS salt FROM organisers WHERE (id=" + id + ");");
            String salt;
            if (set.next()) {
                salt = set.getString("salt");
            } else {
                salt = null;
            }
            set.close();
            return salt;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Player createPlayer(String email, String hash, String salt, String username) {
        try {
            ResultSet set = executeMysqlQuery("SELECT * FROM players WHERE (email=?);", new StringParam(email));
            if (set.next()) {
                set.close();
                return null;
            }
            set.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        MysqlPlayerAdapter adapter;
        try {
            adapter = new MysqlPlayerAdapter(username, email, hash, salt);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        adapter.setEmail(email);
        adapter.setPassword(hash, salt);
        adapter.setUsername(username);
        return new Player(adapter);
    }

    @Override
    public Organiser createOrganiser(String email, String hash, String salt) {
        try {
            ResultSet set
                    = executeMysqlQuery("SELECT * FROM organisers WHERE (email=?);", new StringParam(email));
            if (set.next()) {
                set.close();
                return null;
            }
            set.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        MysqlOrganiserAdapter adapter;
        try {
            adapter = new MysqlOrganiserAdapter(email, hash, salt);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return new Organiser(adapter);
    }

    @Override
    public void registerGame(Organiser organiser, Game game) {
        if (!gameMap.containsKey(game)) {
            gameMap.put(game, organiser);
            try {
                executeMysqlUpdate("UPDATE games SET organiserId=" + organiser.getId() + " WHERE"
                        + " id=" + game.getId() + ";");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeGame(Game game) {
        gameMap.remove(game);
        try {
            executeMysqlUpdate("DELETE FROM games WHERE (id=" + game.getId() + ");");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes the given Mysql-query on the main database
     * @param query query to execute
     * @param params params to execute the query with
     * @return ResultSet of the operation
     * @throws SQLException Thrown when there is an error executing the given statement
     */
    ResultSet executeMysqlQuery(@Language("sql") String query, Param... params) throws SQLException {
        return executeMysqlQuery(query, PRODUCT_DATABASE_NAME, params);
    }

    /**
     * Executes the given Mysql-query on the given database
     * @param query prepared-statement query to execute
     * @param databaseName database to execute the query on
     * @param params parameters to execute the query with
     * @return ResultSet of the operation
     * @throws SQLException Thrown when there is an error executing the given statement
     */
    ResultSet executeMysqlQuery(@Language("sql") String query, String databaseName, Param... params)
            throws SQLException {
        MysqlDataSource dataSource = dataSources.getOrDefault(databaseName, createDataSource(databaseName));
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            params[i].apply(statement, i + 1);
        }
        return statement.executeQuery();
    }

    /**
     * Executes the given Mysql-Update on the main database
     * @param update update query to execute
     * @param params params to execute the query with
     * @throws SQLException Thrown when there is an error executing the given statement
     */
    void executeMysqlUpdate(@Language("sql") String update, Param... params) throws SQLException {
        executeMysqlUpdate(update, PRODUCT_DATABASE_NAME, params);
    }

    /**
     * Executes the given Mysql-Update on the given database
     * @param update prepared-statement update to execute
     * @param databaseName database to execute the query on
     * @param params params to execute the query with
     * @throws SQLException Thrown when there is an error executing the given statement
     */
    void executeMysqlUpdate(@Language("sql") String update, String databaseName, Param... params) throws SQLException {
        MysqlDataSource dataSource = dataSources.getOrDefault(databaseName, createDataSource(databaseName));
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(update);
        for (int i = 0; i < params.length; i++) {
            params[i].apply(statement, i + 1);
        }
        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    private MysqlDataSource createDataSource(String databaseName) {
        MysqlDataSource source = new MysqlDataSource();
        source.setServerName(hostname);
        source.setPort(port);
        source.setUser(username);
        source.setPassword(password);
        source.setDatabaseName(databaseName);
        try {
            source.setServerTimezone("CET");
            source.setCreateDatabaseIfNotExist(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dataSources.put(databaseName, source);
        return source;
    }

    /**
     * Gets the next id an object from the given table will have
     * @param tableName name of the table to get the next id from
     * @return next id
     * @throws SQLException Thrown if an error occurs while communicating with the database
     */
    int getNextIdOfTable(String tableName) throws SQLException {
        ResultSet set = executeMysqlQuery("SELECT MAX(id) AS id FROM " + tableName + ";");
        if (!set.next()) {
            set.close();
            return 1;
        } else {
            int tmp = set.getInt("id") + 1;
            set.close();
            return tmp;
        }
    }

    /**
     * Gets the active games the given {@link PlayerAdapter} participates in or is invited to
     * @param adapter adapter to get games for
     * @return active games
     */
    Collection<Game> getActiveGames(PlayerAdapter adapter) {
        return getActiveGames().stream().filter(game ->
                game.getPlayingPlayers().stream().anyMatch(player -> player.getId() == adapter.getID())
                        || game.getInvitedPlayers().contains(adapter.getEmail()))
                .collect(Collectors.toSet());
    }

    /**
     * Gets the terminated games the given {@link PlayerAdapter} participates in or is invited to
     * @param adapter adapter to get games for
     * @return terminated games
     */
    Collection<Game> getTerminatedGames(PlayerAdapter adapter) {
        return getTerminatedGames().stream().filter(game ->
                game.getPlayingPlayers().stream().anyMatch(player -> player.getId() == adapter.getID())
                        || game.getInvitedPlayers().contains(adapter.getEmail()))
                .collect(Collectors.toSet());
    }

    /**
     * Gets the active games owned by the given {@link OrganiserAdapter}
     * @param adapter adapter to get games for
     * @return active games
     */
    Collection<Game> getActiveGames(OrganiserAdapter adapter) {
        Collection<Game> games = new HashSet<>();
        gameMap.forEach((game, organiser) -> {
            if (organiser.getId() == adapter.getID() && !game.isTerminated()) {
                games.add(game);
            }
        });
        return games;
    }

    /**
     * Gets the terminated games owned by the given {@link OrganiserAdapter}
     * @param adapter adapter to get games for
     * @return terminated games
     */
    Collection<Game> getTerminatedGames(OrganiserAdapter adapter) {
        Collection<Game> games = new HashSet<>();
        gameMap.forEach((game, organiser) -> {
            if (organiser.getId() == adapter.getID() && game.isTerminated()) {
                games.add(game);
            }
        });
        return games;
    }

    private Collection<Game> getActiveGames() {
        return gameMap.keySet().stream().filter(game -> !game.isTerminated()).collect(Collectors.toSet());
    }

    private Collection<Game> getTerminatedGames() {
        return gameMap.keySet().stream().filter(Game::isTerminated).collect(Collectors.toSet());
    }

    /**
     * Gets all games a player is associated with
     * @param playerId players id
     * @return games
     */
    Collection<Game> getGames(int playerId) {
        return gameMap.keySet().stream().filter(game ->
                game.getPlayingPlayers().stream().anyMatch(player -> player.getId() == playerId))
                .collect(Collectors.toSet());
    }
}
