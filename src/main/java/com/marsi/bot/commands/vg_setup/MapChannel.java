package com.marsi.bot.commands.vg_setup;

import com.marsi.bot.objects.DiscordCommand;
import com.marsi.bot.objects.DiscordCommandContext;

import java.util.List;

public class MapChannel extends DiscordCommand {
    @Override
    public String getName() {
        return "map-channel";
    }

    @Override
    public String getDescription() {
        return "Maps a channel for VG.";
    }

    @Override
    public List<String> getAliases() {
        return List.of("map-ch");
    }

    @Override
    public void execute(DiscordCommandContext context) {

    }
}
