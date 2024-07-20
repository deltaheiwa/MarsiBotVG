package com.marsi.vg.entities;

import com.marsi.vg.enums.RoleFaction;
import com.marsi.vg.enums.RoleSubAlignment;

import java.util.List;

public abstract class Role {
    private String name;
    private String description;
    private RoleFaction faction;
    private RoleSubAlignment subAlignment;
    private String aura;
    private List<ActiveAbility> activeAbilities;
    private List<PassiveAbility> passiveAbilities;
    private int baseDayVisits;
    private int baseNightVisits;

}
