package com.marsi.bot.commands.admin;

import com.marsi.bot.Marsi;
import com.marsi.bot.objects.DiscordCommand;
import com.marsi.bot.objects.DiscordCommandContext;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.Arrays;
import java.util.List;

public class SetPrefix extends DiscordCommand {
    @Override
    public void execute(DiscordCommandContext context) {
        if (context.getArgs().isEmpty()) {
            context.sendMessage("You need to provide a non-empty prefix.");
            return;
        }

        String newPrefix = context.getArg(0);
        Marsi.getInstance().getPrefixDatabase().setPrefix(context.getGuild().getId(), newPrefix);
        context.sendMessage("Prefix set to " + newPrefix);
    }

    @Override
    public String getName() {
        return "set-prefix";
    }

    @Override
    public String getDescription() {
        return "Sets the bot's prefix.";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("prefix", "change-prefix");
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash(getName(), getDescription()).addOption(OptionType.STRING, "prefix", "The new prefix.", true);
    }
}
