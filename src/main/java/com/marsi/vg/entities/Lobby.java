package com.marsi.vg.entities;

import com.marsi.commons.database.Row;
import com.marsi.vg.enums.LobbyStatus;
import org.apache.commons.collections4.set.ListOrderedSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Lobby {
    private final int id;
    private final String serverId;
    private LobbyStatus status;
    private int maxPlayers;
    private ListOrderedSet<Role> role_list;

    private List<Player> players;
    private Map<String, Long> channelMap;
    private List<Location> houses;


    public Lobby(int id, String serverId, LobbyStatus status, int maxPlayers) {
        this.id = id;
        this.serverId = serverId;
        this.status = status;
        this.maxPlayers = maxPlayers;

        this.role_list = new ListOrderedSet<>();
        this.players = new ArrayList<>();
        this.channelMap = Stream.of(new Object[][] {
                { "rules", 0L },
                { "mechanics", 0L },
                { "pg-announcements", 0L },
                { "player-list", 0L },
                { "announcements", 0L },
                { "overseer-status", 0L },
                { "map", 0L },
                { "death-reports", 0L },
                { "megaphone", 0L },
                { "day-chat", 0L },
                { "vote-channel", 0L },
                { "vote-count", 0L },
                { "graveyard", 0L },
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Long) data[1]));

        this.houses = new ArrayList<>();
    }

    public static Lobby fromRow(Row row) {
        return new Lobby(
                (int) row.get("id"),
                (String) row.get("server_id"),
                LobbyStatus.valueOf((String) row.get("status_id")),
                (int) row.get("max_players")
        );
    }

    public int getId() {
        return id;
    }

    public String getServerId() {
        return serverId;
    }

    public LobbyStatus getStatus() {
        return status;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public Map<String, Long> getChannelMap() {
        return channelMap;
    }

    public void setChannelId(String channelCode, Long channelId) {
        if (!channelMap.containsKey(channelCode)) {
            throw new IllegalArgumentException("Channel code not found");
        }
        channelMap.put(channelCode, channelId);
    }

    @Override
    public String toString() {
        return String.format("Lobby %s: %s, in %s", id, status, serverId);
    }
}
