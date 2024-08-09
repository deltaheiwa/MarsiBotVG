package com.marsi.vg;

import com.marsi.commons.database.Row;
import com.marsi.vg.database.VillageGameDatabase;
import com.marsi.vg.entities.Lobby;
import com.marsi.vg.events.initializers.PhaseChangeInitializer;
import com.marsi.vg.managers.LobbyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class Launcher {
    private static final Logger logger = LoggerFactory.getLogger("Launcher");
    private static final Map<String, Future<?>> futures = new ConcurrentHashMap<>();
    private static VillageGameDatabase database = null;
    private static final ExecutorService executor = Executors.newFixedThreadPool(2);

    private static final LobbyManager lobbyManager = new LobbyManager();


    public static void initialize() {
        Callable<Void> databaseTask = () -> {
            database = new VillageGameDatabase("jdbc:sqlite:./src/main/resources/databases/vg.db");
            database.createTables();
            database.populateStatusTables();
            return null;
        };

        Callable<Void> gameTask = () -> {
            futures.get("database").get(); // Wait for database to be initialized
            loadLobbies();
            return null;
        };

        futures.put("database", executor.submit(databaseTask));
        futures.put("game", executor.submit(gameTask));
    }


    public static VillageGameDatabase getDatabase() {
        return database;
    }

    private static void loadLobbies() {
        logger.info("Loading lobbies");
        try {
            List<Row> rows = database.getAllOpenLobbies().get();
            for (Row row : rows) {
                logger.debug("Loading lobby: {}", row.toString());
                lobbyManager.addLobby(Lobby.fromRow(row));
            }
        } catch (Exception e) {
            logger.error("Error loading lobbies", e);
        }
    }

    public static LobbyManager getLobbyManager() {
        return lobbyManager;
    }
}
