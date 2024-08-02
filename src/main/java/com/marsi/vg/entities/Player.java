package com.marsi.vg.entities;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.Channel;

public class Player {
    private Member discordMember;
    private int rcId;
    private Channel roleChannel;

    private Role role;
    private boolean isAlive;
    private int availableDayVisits;
    private int availableNightVisits;
    
}
