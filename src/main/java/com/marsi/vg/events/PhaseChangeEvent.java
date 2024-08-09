package com.marsi.vg.events;

import com.marsi.vg.entities.Phase;
import com.marsi.vg.enums.PhaseType;

import java.util.EventObject;

public class PhaseChangeEvent extends EventObject {
    private Phase phase;
    public PhaseChangeEvent(Object source, Phase phase) {
        super(source);
        this.phase = phase;
    }
}
