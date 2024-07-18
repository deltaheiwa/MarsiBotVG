package com.marsi.console.commands;

public class Status extends ConsoleCommand {
    public Status() {
        super("status");
    }

    @Override
    public void execute() {
        System.out.println("Status: OK");
    }
}
