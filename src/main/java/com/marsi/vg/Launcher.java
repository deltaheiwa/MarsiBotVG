package com.marsi.vg;

import com.marsi.vg.database.VillageGameDatabase;

import java.util.HashMap;

public class Launcher {
    private static final HashMap<String, Thread> threads = new HashMap<>();
    private static VillageGameDatabase database = null;

    public static void initialize() {
        // Initialize the game
        threads.put("database", new Thread(() -> {
            database = new VillageGameDatabase("jdbc:sqlite:./src/main/resources/databases/vg.db");
            database.createTables();
            database.populateStatusTables();
        }, "database"));

        threads.get("database").start();
    }

    public static VillageGameDatabase getDatabase() {
        return database;
    }
}
