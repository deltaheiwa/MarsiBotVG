package com.marsi.vg.events.initializers;

import com.marsi.vg.events.PhaseChangeEvent;
import com.marsi.vg.events.listeners.PhaseChangeListener;

import java.util.ArrayList;
import java.util.List;

public class PhaseChangeInitializer {
    private static final List<PhaseChangeListener> listeners = new ArrayList<>();

    public static void addListener(PhaseChangeListener listener) {
        listeners.add(listener);
    }

    public static void changePhase(Object source) {
        PhaseChangeEvent event = new PhaseChangeEvent(source);
        listeners.forEach(listener -> {
            listener.onPhaseChange(event);
        });
    }
}
