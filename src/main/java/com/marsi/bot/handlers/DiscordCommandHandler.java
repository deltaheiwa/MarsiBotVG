package com.marsi.bot.handlers;

import com.marsi.bot.Marsi;
import com.marsi.bot.objects.DiscordCommand;
import com.marsi.bot.objects.DiscordCommandContext;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.*;

public class DiscordCommandHandler {
    private final Map<String, DiscordCommand> commands = new HashMap<>();
    private final Map<String, String> aliases = new HashMap<>();
    private final String fixedPrefix = "m-";

    private void addCommand(DiscordCommand command) {
        String primaryName = command.getName().toLowerCase();
        commands.put(primaryName, command);
        command.getAliases().forEach(alias -> aliases.put(alias.toLowerCase(), primaryName));
    }

    public void fetchCommandsFromBot() {
        Marsi.getInstance().getCommands().forEach(this::addCommand);
    }

    private DiscordCommand getCommandByName(String name) {
        String primaryName = aliases.getOrDefault(name, name);
        return commands.get(primaryName);
    }

    public void handleTextCommand(MessageReceivedEvent event, String dynamicPrefix) {
        String rawMessage = event.getMessage().getContentRaw();
        String prefix = rawMessage.startsWith(fixedPrefix) ? fixedPrefix : dynamicPrefix;

        if (!rawMessage.startsWith(prefix)) {
            return;
        }

        String[] split = rawMessage.substring(prefix.length()).split("\\s+");
        String commandName = split[0].toLowerCase();
        List<String> args = Arrays.asList(split).subList(1, split.length);

        DiscordCommand command = getCommandByName(commandName);

        if (command != null) {
            command.execute(new DiscordCommandContext(event, args));
        }
    }

    public void handleSlashCommand(SlashCommandInteractionEvent event) {
        DiscordCommand command = commands.get(event.getName().toLowerCase());

        if (command != null) {
            command.execute(new DiscordCommandContext(event));
        }
    }

    public Map<String, DiscordCommand> getCommands() {
        return commands;
    }
}
