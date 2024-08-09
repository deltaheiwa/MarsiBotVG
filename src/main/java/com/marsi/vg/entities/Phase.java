package com.marsi.vg.entities;

import com.marsi.vg.enums.PhaseType;

/**
 * Represents a phase in the game. Basically a struct with a phase type and a counter.
 */
public class Phase {
    private final PhaseType phaseType;
    private final byte phaseCounter;

    public Phase(PhaseType phaseType, byte phaseCounter) {
        this.phaseType = phaseType;
        this.phaseCounter = phaseCounter;
    }

    public PhaseType getPhaseType() {
        return phaseType;
    }

    public byte getPhaseCounter() {
        return phaseCounter;
    }
}
