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
    private Map<String, String> channelMap;
    private List<Location> houses;


    public Lobby(int id, String serverId, LobbyStatus status, int maxPlayers) {
        this.id = id;
        this.serverId = serverId;
        this.status = status;
        this.maxPlayers = maxPlayers;

        this.role_list = new ListOrderedSet<>();
        this.players = new ArrayList<>();
        this.channelMap = Stream.of(new String[][] {
                { "rules", "" },
                { "mechanics", "" },
                { "pg-announcements", "" },
                { "player-list", "" },
                { "announcements", "" },
                { "overseer-status", "" },
                { "map", "" },
                { "death-reports", "" },
                { "megaphone", "" },
                { "day-chat", "" },
                { "vote-channel", "" },
                { "vote-count", "" },
                { "graveyard", "" },
        }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

        this.houses = new ArrayList<>();
    }

    public static Lobby fromRow(Row row) {
        String serverId = (String) row.get("server_id");
        Lobby lobby = new Lobby(
                (int) row.get("id"),
                serverId,
                LobbyStatus.fromDatabaseId((int) row.get("status_id")),
                (int) row.get("max_players")
        );



        return lobby;
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

    public Map<String, String> getChannelMap() {
        return channelMap;
    }

    public void setChannelId(String channelCode, String channelId) {
        if (!channelMap.containsKey(channelCode)) {
            throw new IllegalArgumentException("Channel code not found");
        }
        channelMap.put(channelCode, channelId);
    }

    public void addHouse(Location house) {
        houses.add(house);
    }

    public List<Location> getHouses() {
        return houses;
    }

    @Override
    public String toString() {
        return String.format("Lobby %s: %s, in %s", id, status, serverId);
    }
}
