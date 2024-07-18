package com.marsi.console;

import com.marsi.console.commands.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleThread extends Thread {
    Map<String, ConsoleCommand> commands;

    public ConsoleThread() {
        this.commands = new HashMap<>();
        this.commands.put("exit", new Exit());
        this.commands.put("status", new Status());
    }

    public ConsoleThread(Map<String, ConsoleCommand> commands) {
        this.commands = commands;
    }

    @Override
    public void run() {
        while (true) {
            System.out.print("> ");
            Scanner scanner = new Scanner(System.in);

            String command = scanner.nextLine();

            if (commands.containsKey(command)) {
                commands.get(command).execute();
            } else {
                System.out.println("Unknown command");
            }
        }
    }
    
}
