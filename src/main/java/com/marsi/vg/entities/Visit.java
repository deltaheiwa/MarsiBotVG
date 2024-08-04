package com.marsi.vg.entities;

import com.marsi.vg.enums.UsagePhase;
import com.marsi.vg.enums.VisitType;

public class Visit {
    private final VisitType type;
    private final UsagePhase phase;

    public Visit(VisitType type, UsagePhase phase) {
        this.type = type;
        this.phase = phase;
    }

    public VisitType getType() {
        return type;
    }

    public UsagePhase getPhase() {
        return phase;
    }
}
