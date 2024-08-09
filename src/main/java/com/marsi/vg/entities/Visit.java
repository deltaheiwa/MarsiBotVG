package com.marsi.vg.entities;

import com.marsi.vg.enums.PhaseType;
import com.marsi.vg.enums.VisitType;

public class Visit {
    private final VisitType type;
    private final PhaseType phaseType;

    public Visit(VisitType type, PhaseType phaseType) {
        this.type = type;
        this.phaseType = phaseType;
    }

    public VisitType getType() {
        return type;
    }

    public PhaseType getPhase() {
        return phaseType;
    }
}
