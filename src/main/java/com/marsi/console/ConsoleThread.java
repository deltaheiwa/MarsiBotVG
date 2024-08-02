package com.marsi.console;

import com.marsi.console.commands.*;

import java.util.*;

public class ConsoleThread extends Thread {
    Map<String, ConsoleCommand> commands;

    public ConsoleThread() {
        this.commands = new HashMap<>();
        this.commands.put("exit", new Exit());
        this.commands.put("status", new Status());
        this.commands.put("inspect", new Inspect());
    }

    public ConsoleThread(Map<String, ConsoleCommand> commands) {
        this.commands = commands;
    }

    @Override
    public void run() {
        while (true) {
            System.out.print("> ");
            Scanner scanner = new Scanner(System.in);

            List<String> input = List.of(scanner.nextLine().split(" "));

            String command = input.get(0);

            if (commands.containsKey(command)) {
                commands.get(command).execute(input);
            } else {
                System.out.println("Unknown command");
            }
        }
    }
    
}
