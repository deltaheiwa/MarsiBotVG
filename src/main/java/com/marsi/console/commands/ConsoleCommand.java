package com.marsi.console.commands;

import java.util.List;

public abstract class ConsoleCommand {
    final String name;

    public abstract void execute(List<String> args);

    protected ConsoleCommand(String name) {
        this.name = name;
    }

}
