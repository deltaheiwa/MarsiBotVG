package com.marsi.vg.entities;

import com.marsi.vg.enums.AbilityUsagePhase;
import com.marsi.vg.enums.AbilityUseType;

public abstract class ActiveAbility extends Ability {
    private AbilityUsagePhase usagePhase;
    private int usesPerGame;  // -1 for unlimited
}
