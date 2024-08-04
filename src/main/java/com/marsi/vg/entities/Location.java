package com.marsi.vg.entities;

import com.marsi.vg.enums.LocationStatus;
import com.marsi.vg.enums.LocationType;
import net.dv8tion.jda.api.entities.channel.Channel;

import java.util.ArrayList;
import java.util.List;

public class Location {
    private String id;
    private int lobbyId;
    private String name;
    private Long channelId;
    private Channel channel;
    private LocationStatus status;
    private LocationType type;

    private boolean isPublic;
    private List<Player> players = new ArrayList<>();

    public Location(String id, int lobbyId, String name, Long channelId) {
        this.id = id;
        this.lobbyId = lobbyId;
        this.name = name;

        if (id.startsWith("h")) {
            this.type = LocationType.HOUSE;
        } else {
            this.type = LocationType.NULL;
        }

        this.channelId = channelId;
        this.status = LocationStatus.OPENED;
        this.isPublic = true;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getChannelId() {
        return channelId;
    }

    public int getLobbyId() {
        return lobbyId;
    }

    public Channel getChannel() {
        return channel;
    }

    public LocationStatus getStatus() {
        return status;
    }

    public int getLocationStatusAsDatabaseId() {
        return status.asDatabaseId();
    }
}
