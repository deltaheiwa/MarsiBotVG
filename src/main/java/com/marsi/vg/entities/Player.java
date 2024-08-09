package com.marsi.vg.entities;

import com.marsi.vg.events.PhaseChangeEvent;
import com.marsi.vg.events.initializers.PhaseChangeInitializer;
import com.marsi.vg.events.listeners.PhaseChangeListener;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.List;

public class Player implements PhaseChangeListener {
    private Member discordMember;
    private TextChannel roleChannel;

    private Role role;
    private boolean isAlive;
    private List<Visit> availableVisits;

    public Player(Member discordMember, TextChannel roleChannel) {
        this.discordMember = discordMember;
        this.roleChannel = roleChannel;

        this.role = null;
        this.isAlive = true;
        this.availableVisits = null;

        PhaseChangeInitializer.addListener(this);
    }

    public String getUserId() {
        return discordMember.getId();
    }

    @Override
    public void onPhaseChange(PhaseChangeEvent event) {
        System.out.println("Phase change event received by " + discordMember.getEffectiveName());
    }
}
