package com.marsi.console.commands;

import com.marsi.vg.Launcher;
import com.marsi.vg.entities.Lobby;

import java.util.Iterator;
import java.util.List;

public class Inspect extends ConsoleCommand {

    public Inspect() {
        super("inspect");
    }
    @Override
    public void execute(List<String> args) {
        String target = args.get(1).toLowerCase();
        switch (target) {
            case "lobbies":
                for (Lobby lobby : Launcher.getLobbyManager().getLobbies()) {
                    System.out.println(lobby);
                }
                break;
        }

    }
}
