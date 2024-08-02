package com.marsi.commons.database;

import com.marsi.bot.Marsi;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Row {
    private final HashMap<String, Object> data;

    public Row() {
        this.data = new HashMap<>();
    }

    public Row(HashMap<String, Object> data) {
        this.data = data;
    }

    public Object get(String key) {
        return data.get(key);
    }

    public void fetchRow(ResultSet rs, List<String> columns) {
        try {
            for (String column : columns) {
                data.put(column, rs.getObject(column));
            }
        } catch (Exception e) {
            Marsi.getInstance().getLogger().error("Error fetching row", e);
        }
    }
}
