package com.marsi.vg.entities;

import com.marsi.vg.enums.Phase;
import com.marsi.vg.enums.VisitType;

public class Visit {
    private final VisitType type;
    private final Phase phase;

    public Visit(VisitType type, Phase phase) {
        this.type = type;
        this.phase = phase;
    }

    public VisitType getType() {
        return type;
    }

    public Phase getPhase() {
        return phase;
    }
}
