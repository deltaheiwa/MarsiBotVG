package com.marsi.console.commands;

import java.util.List;

public class Status extends ConsoleCommand {
    public Status() {
        super("status");
    }

    @Override
    public void execute(List<String> args) {
        System.out.println("Status: OK");
    }
}
