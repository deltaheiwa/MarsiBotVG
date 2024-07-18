package com.marsi.commons.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class SQLiteDatabase implements Database {
    private final String uri;

    public SQLiteDatabase(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public Connection connect() throws SQLException {
        Connection connection = DriverManager.getConnection(uri);
        connection.setAutoCommit(false);
        return connection;
    }

    public abstract void createTables();

    public DatabaseType getType() {
        return DatabaseType.SQLITE;
    }
}
