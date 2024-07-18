package com.marsi.bot.listeners;

import com.marsi.bot.Marsi;
import com.marsi.console.ConsoleThread;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class GeneralBotListener extends ListenerAdapter {
    @Override
    public void onReady(@NotNull ReadyEvent event) {
        ConsoleThread consoleThread = new ConsoleThread();
        consoleThread.start();
    }
}
