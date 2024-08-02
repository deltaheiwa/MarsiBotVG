package com.marsi.vg;

import com.marsi.commons.database.Row;
import com.marsi.vg.database.VillageGameDatabase;
import com.marsi.vg.entities.Lobby;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;
import java.util.concurrent.*;

public class Launcher {
    private static final Logger logger = LoggerFactory.getLogger("Launcher");
    private static final ConcurrentHashMap<String, Thread> threads = new ConcurrentHashMap<>();
    private static VillageGameDatabase database = null;
    private static final ExecutorService executor = Executors.newFixedThreadPool(2);


    private static final List<Lobby> lobbies = new CopyOnWriteArrayList<>();
    private static final CountDownLatch latch = new CountDownLatch(2);

    public static void initialize() {
        threads.put("database", new Thread(() -> {
            try {
                database = new VillageGameDatabase("jdbc:sqlite:./src/main/resources/databases/vg.db");
                database.createTables();
                database.populateStatusTables();
            } finally {
                latch.countDown();
            }
        }, "database"));

        threads.put("game", new Thread(() -> {
            try {
                logger.debug("Waiting for database to initialize");
                latch.await(); // waits for the database to be initialized, otherwise it will crash
                loadLobbies();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Error initializing game", e);
            }
        }, "game"));

        threads.forEach((name, thread) -> thread.start());
    }

    public static VillageGameDatabase getDatabase() {
        return database;
    }

    private static void loadLobbies() {
        logger.info("Loading lobbies");
        try {
            List<Row> rows = database.getAllOpenLobbies().get();
            for (Row row : rows) {
                lobbies.add(Lobby.fromRow(row));
            }
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error loading lobbies", e);
        }
    }

    public static List<Lobby> getLobbies() {
        return lobbies;
    }
}
