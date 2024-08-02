package com.marsi.console.commands;

import com.marsi.bot.Marsi;

import java.util.List;

public class Exit extends ConsoleCommand {
    public Exit() {
        super("exit");
    }

    @Override
    public void execute(List<String> args) {
        Marsi.getInstance().getShardManager().shutdown();
        System.exit(0);
    }
}
