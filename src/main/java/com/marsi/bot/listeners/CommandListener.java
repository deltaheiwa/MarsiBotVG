package com.marsi.bot.listeners;

import com.marsi.bot.Marsi;
import com.marsi.bot.handlers.DiscordCommandHandler;
import com.marsi.console.ConsoleThread;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class CommandListener extends ListenerAdapter {
    private final DiscordCommandHandler commandHandler = new DiscordCommandHandler();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String dynamicPrefix = event.isFromGuild() ? Marsi.getInstance().getPrefixDatabase().getPrefix(event.getGuild().getId()) : "tk-";

        commandHandler.handleTextCommand(event, dynamicPrefix);
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        commandHandler.handleSlashCommand(event);
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        commandHandler.fetchCommandsFromBot();
    }
}
