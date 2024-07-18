package com.marsi.console.commands;

public abstract class ConsoleCommand {
    final String name;

    public abstract void execute();

    protected ConsoleCommand(String name) {
        this.name = name;
    }

}
