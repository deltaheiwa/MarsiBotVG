package com.marsi.vg.database;

import com.marsi.commons.database.SQLiteDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class VillageGameDatabase extends SQLiteDatabase {
    private static final Logger logger = LoggerFactory.getLogger(VillageGameDatabase.class);
    private final ExecutorService executor;

    public VillageGameDatabase(String uri) {
        super(uri);
        executor = Executors.newFixedThreadPool(10);
    }

    @Override
    public void createTables() {
        String[] createTableStatements = {
                // Lobby Status
                "CREATE TABLE IF NOT EXISTS lobby_status ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "status TEXT NOT NULL);",

                // Lobby
                "CREATE TABLE IF NOT EXISTS lobby ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "server_id TEXT NOT NULL,"
                        + "status_id INTEGER NOT NULL,"
                        + "max_players INTEGER NOT NULL,"
                        + "FOREIGN KEY (status_id) REFERENCES lobby_status(id));",

                // Player
                "CREATE TABLE IF NOT EXISTS player ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "discord_id TEXT NOT NULL,"
                        + "role_id INTEGER NULL,"
                        + "house_id INTEGER NULL,"
                        + "lobby_id INTEGER NOT NULL,"
                        + "FOREIGN KEY (lobby_id) REFERENCES lobby(id));",

                // Role
                "CREATE TABLE IF NOT EXISTS role ("
                        + "id TEXT PRIMARY KEY,"
                        + "name TEXT NOT NULL,"
                        + "lobby_id INTEGER NULL);",

                // Ability
                "CREATE TABLE IF NOT EXISTS ability ("
                        + "id TEXT PRIMARY KEY,"
                        + "name TEXT NOT NULL,"
                        + "role_id TEXT NOT NULL,"
                        + "FOREIGN KEY (role_id) REFERENCES role(id));",

                // Location Status
                "CREATE TABLE IF NOT EXISTS location_status ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "status TEXT NOT NULL);",

                // Location
                "CREATE TABLE IF NOT EXISTS location ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "channel_id TEXT NOT NULL,"
                        + "name TEXT NOT NULL,"
                        + "lobby_id INTEGER NOT NULL,"
                        + "status_id INTEGER NOT NULL,"
                        + "FOREIGN KEY (lobby_id) REFERENCES lobby(id),"
                        + "FOREIGN KEY (status_id) REFERENCES location_status(id));",

                // Votes
                "CREATE TABLE IF NOT EXISTS vote ("
                        + "voter_id INTEGER NOT NULL,"
                        + "voted_id INTEGER NOT NULL,"
                        + "day_counter INTEGER NOT NULL,"
                        + "PRIMARY KEY (voter_id, voted_id, day_counter),"
                        + "FOREIGN KEY (voter_id) REFERENCES player(id),"
                        + "FOREIGN KEY (voted_id) REFERENCES player(id));",

                // Phase
                "CREATE TABLE IF NOT EXISTS phase ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "phase TEXT NOT NULL);",

                // Ability Target Type
                "CREATE TABLE IF NOT EXISTS ability_target_type ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "type TEXT NOT NULL);",

                // Ability Log
                "CREATE TABLE IF NOT EXISTS ability_log ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "ability_id TEXT NOT NULL,"
                        + "player_id INTEGER NOT NULL,"
                        + "target_type_id INTEGER NOT NULL,"
                        + "phase_id INTEGER NOT NULL,"
                        + "phase_counter INTEGER NOT NULL,"
                        + "timestamp TEXT NOT NULL,"
                        + "FOREIGN KEY (ability_id) REFERENCES ability(id),"
                        + "FOREIGN KEY (player_id) REFERENCES player(id),"
                        + "FOREIGN KEY (target_type_id) REFERENCES ability_target_type(id),"
                        + "FOREIGN KEY (phase_id) REFERENCES phase(id));",

                // Ability Target Log
                "CREATE TABLE IF NOT EXISTS ability_target_log ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "ability_log_id INTEGER NOT NULL,"
                        + "target_id INTEGER NOT NULL,"
                        + "FOREIGN KEY (ability_log_id) REFERENCES ability_log(id));",

                // Cause of Death
                "CREATE TABLE IF NOT EXISTS cause_of_death ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "cause TEXT NOT NULL);",

                // Death Log
                "CREATE TABLE IF NOT EXISTS death_log ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "player_id INTEGER NOT NULL,"
                        + "phase_id INTEGER NOT NULL,"
                        + "phase_counter INTEGER NOT NULL,"
                        + "cause_id INTEGER NOT NULL,"
                        + "timestamp TEXT NOT NULL,"
                        + "FOREIGN KEY (player_id) REFERENCES player(id),"
                        + "FOREIGN KEY (phase_id) REFERENCES phase(id),"
                        + "FOREIGN KEY (cause_id) REFERENCES cause_of_death(id));",

                // Visit Log
                "CREATE TABLE IF NOT EXISTS visit_log ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + "visitor_id INTEGER NOT NULL,"
                        + "location_id INTEGER NOT NULL,"
                        + "phase_id INTEGER NOT NULL,"
                        + "phase_counter INTEGER NOT NULL,"
                        + "timestamp TEXT NOT NULL,"
                        + "FOREIGN KEY (visitor_id) REFERENCES player(id),"
                        + "FOREIGN KEY (location_id) REFERENCES location(id),"
                        + "FOREIGN KEY (phase_id) REFERENCES phase(id));"
        };

        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            for (String createTable : createTableStatements) {
                statement.execute(createTable);
            }
            connection.commit();
            logger.info("Tables created");
        } catch (Exception e) {
            logger.error("Error creating tables", e);
        }
    }

    public void populateStatusTables() {
        String[] populateStatusTableStatements = {
                "INSERT INTO lobby_status (status) VALUES ('OPEN');",
                "INSERT INTO lobby_status (status) VALUES ('IN_PROGRESS');",
                "INSERT INTO lobby_status (status) VALUES ('CLOSED');",
                "INSERT INTO location_status (status) VALUES ('OPEN');",
                "INSERT INTO location_status (status) VALUES ('UNAVAILABLE');",
                "INSERT INTO phase (phase) VALUES ('DAY');",
                "INSERT INTO phase (phase) VALUES ('NIGHT');",
                "INSERT INTO ability_target_type (type) VALUES ('SELF');",
                "INSERT INTO ability_target_type (type) VALUES ('SINGLE_PLAYER');",
                "INSERT INTO ability_target_type (type) VALUES ('MULTIPLE_PLAYERS');",
                "INSERT INTO ability_target_type (type) VALUES ('HOUSE');",
                "INSERT INTO ability_target_type (type) VALUES ('OTHER');",
                "INSERT INTO cause_of_death (cause) VALUES ('LYNCH');",
                "INSERT INTO cause_of_death (cause) VALUES ('ABILITY');",
                "INSERT INTO cause_of_death (cause) VALUES ('MODKILL');"
        };

        try (Connection connection = connect()) {
            // Check if status tables are already populated
            if (connection.createStatement().executeQuery("SELECT * FROM cause_of_death").next()) {
                return;
            }
            Statement statement = connection.createStatement();
            for (String populateStatusTable : populateStatusTableStatements) {
                statement.execute(populateStatusTable);
            }
            connection.commit();
            logger.info("Status tables populated");
        } catch (Exception e) {
            logger.error("Error populating status tables", e);
        }
    }

    public Future<ResultSet> getLobbyByServerId(String serverId) {
        Callable<ResultSet> task = () -> {
            try (Connection connection = connect()) {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM lobby WHERE server_id = ?");
                statement.setString(1, serverId);
                return statement.executeQuery();
            } catch (Exception e) {
                logger.error("Error checking if lobby exists", e);
                throw e;
            }
        };
        return executor.submit(task);
    }
}
