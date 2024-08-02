package com.marsi.database;

import com.marsi.commons.database.SQLiteDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class PrefixDatabase extends SQLiteDatabase {
    private final static Logger logger = LoggerFactory.getLogger("PrefixDatabase");

    public PrefixDatabase(String uri) {
        super(uri);
    }

    public void createTables() {
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS prefixes (guild_id TEXT PRIMARY KEY, prefix TEXT)");
            connection.commit();
            logger.info("Tables created");
        } catch (SQLException e) {
            logger.error("Error creating tables", e);
        }
    }

    public void setPrefix(String guildId, String prefix) {
        String sql = "INSERT OR REPLACE INTO prefixes (guild_id, prefix) VALUES (?, ?)";
        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, guildId);
            statement.setString(2, prefix);

            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getPrefix(String guildId) {
        String sql = "SELECT prefix FROM prefixes WHERE guild_id = ?";
        try (Connection connection = connect()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, guildId);

            return statement.executeQuery().getString("prefix");
        } catch (SQLException e) {
            logger.error("Error getting prefix", e);
            return null;
        }
    }
}
