package com.marsi.vg.entities;

import com.marsi.vg.enums.LocationStatus;
import com.marsi.vg.enums.LocationType;
import net.dv8tion.jda.api.entities.channel.Channel;

import java.util.ArrayList;
import java.util.List;

public class Location {
    private String id;
    private String name;
    private Long channelId;
    private Channel channel;
    private LocationStatus status;
    private LocationType type;

    private boolean isPublic;
    private List<Player> players = new ArrayList<>();

    public Location(String id, String name, Long channelId) {
        this.id = id;
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
}
