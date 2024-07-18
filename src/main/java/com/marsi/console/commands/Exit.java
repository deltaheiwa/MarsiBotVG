package com.marsi.console.commands;

import com.marsi.bot.Marsi;

public class Exit extends ConsoleCommand {
    public Exit() {
        super("exit");
    }

    @Override
    public void execute() {
        Marsi.getInstance().getShardManager().shutdown();
        System.exit(0);
    }
}
