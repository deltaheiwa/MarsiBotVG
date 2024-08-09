package com.marsi.bot.commands.vg_setup;

import com.marsi.bot.objects.DiscordCommand;
import com.marsi.bot.objects.DiscordCommandContext;
import com.marsi.vg.Launcher;
import com.marsi.vg.entities.Player;
import com.marsi.vg.events.initializers.PhaseChangeInitializer;

import java.util.List;

public class Test extends DiscordCommand {
    @Override
    public String getName() {
        return "test";
    }

    @Override
    public String getDescription() {
        return "Test command";
    }

    @Override
    public List<String> getAliases() {
        return List.of();
    }

    @Override
    public void execute(DiscordCommandContext context) {
        Launcher.getLobbyManager().getLobbyByServerId(context.getGuild().getId()).registerPlayer(new Player(context.getAuthorMember(), context.getChannel()));

        PhaseChangeInitializer.changePhase(this, null);
    }
}
