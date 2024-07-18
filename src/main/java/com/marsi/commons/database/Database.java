package com.marsi.commons.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface Database {
    Connection connect() throws SQLException;
    void createTables();

    DatabaseType getType();
}
