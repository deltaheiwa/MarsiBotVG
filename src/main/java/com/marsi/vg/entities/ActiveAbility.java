package com.marsi.vg.entities;

import com.marsi.vg.enums.Phase;

public abstract class ActiveAbility extends Ability {
    private Phase usagePhase;
    private int usesPerGame;  // -1 for unlimited
}
