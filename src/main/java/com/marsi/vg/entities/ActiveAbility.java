package com.marsi.vg.entities;

import com.marsi.vg.enums.PhaseType;

public abstract class ActiveAbility extends Ability {
    private PhaseType usagePhaseType;
    private int usesPerGame;  // -1 for unlimited
}
