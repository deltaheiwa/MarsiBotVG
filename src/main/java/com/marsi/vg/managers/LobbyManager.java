package com.marsi.vg.managers;

import com.marsi.vg.entities.Lobby;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LobbyManager {
    private final Map<String, Lobby> lobbies = new ConcurrentHashMap<>();

    public Lobby getLobbyByServerId(String serverId) {
        return lobbies.get(serverId);
    }

    public void addLobby(Lobby lobby) {
        lobbies.put(lobby.getServerId(), lobby);
    }

    public List<Lobby> getLobbies() {
        return (List<Lobby>) lobbies.values();
    }
}
