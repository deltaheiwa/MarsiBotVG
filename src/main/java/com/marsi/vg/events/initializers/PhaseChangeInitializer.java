package com.marsi.vg.events.initializers;

import com.marsi.vg.entities.Phase;
import com.marsi.vg.enums.PhaseType;
import com.marsi.vg.events.PhaseChangeEvent;
import com.marsi.vg.events.listeners.PhaseChangeListener;

import java.util.ArrayList;
import java.util.List;

public class PhaseChangeInitializer {
    private static final List<PhaseChangeListener> listeners = new ArrayList<>();

    public static void addListener(PhaseChangeListener listener) {
        listeners.add(listener);
    }

    public static void changePhase(Object source, Phase phase) {
        PhaseChangeEvent event = new PhaseChangeEvent(source, phase);
        listeners.forEach(listener -> {
            listener.onPhaseChange(event);
        });
    }
}
