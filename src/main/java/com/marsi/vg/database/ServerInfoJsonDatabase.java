package com.marsi.vg.database;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ServerInfoJsonDatabase {
    private static final Logger logger = LoggerFactory.getLogger("ServerInfoJsonDatabase");

    public static void saveServerInfo(String serverId, Map<String, String> channelIds, Map<String, String> rcIds, Map<String, String> housesIds) {
        Gson gson = new Gson();
        String json = "[" + gson.toJson(channelIds) + ",\n" + gson.toJson(rcIds) + ",\n" + gson.toJson(housesIds) + "]";

        try (FileWriter writer = new FileWriter("./src/main/resources/databases/json_data/" + serverId + ".json")) {
            writer.write(json);
            logger.info("Server info saved for server " + serverId);
        } catch (IOException e) {
            logger.error("Error saving server info", e);
        }

    }
}
