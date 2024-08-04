package com.marsi.vg.entities;

import com.marsi.vg.enums.UsagePhase;

public abstract class ActiveAbility extends Ability {
    private UsagePhase usagePhase;
    private int usesPerGame;  // -1 for unlimited
}
