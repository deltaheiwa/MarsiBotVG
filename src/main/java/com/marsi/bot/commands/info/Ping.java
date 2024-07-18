package com.marsi.bot.commands.info;

import com.marsi.bot.Marsi;
import com.marsi.bot.objects.DiscordCommand;
import com.marsi.bot.objects.DiscordCommandContext;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.List;

public class Ping extends DiscordCommand {
    @Override
    public void execute(DiscordCommandContext context) {
        String pongMessage = String.format("Pong %sms", Marsi.getInstance().getShardManager().getShards().get(0).getGatewayPing());
        context.sendMessage(pongMessage);
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getDescription() {
        return "Pings the bot.";
    }

    @Override
    public List<String> getAliases() {
        return List.of();
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash(getName(), getDescription());
    }

}
