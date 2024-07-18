package com.marsi;

import com.marsi.bot.Marsi;
import com.marsi.console.ConsoleThread;

public class Main {
    public static void main(String[] args) {
        Marsi marsi = Marsi.getInstance();
        marsi.run();
    }
}
