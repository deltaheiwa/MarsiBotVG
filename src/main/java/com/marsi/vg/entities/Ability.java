package com.marsi.vg.entities;

import com.marsi.vg.enums.AbilityCategory;
import com.marsi.vg.enums.AbilityTarget;
import com.marsi.vg.enums.AbilityUseType;

import java.util.EnumSet;

public abstract class Ability implements Comparable<Ability> {
    private String name;
    private String description;
    private EnumSet<AbilityCategory> categories;
    private int priority;  // Might scrape this. Can be determined by the ability categories and role alignment
    private AbilityTarget allowedTarget;
    private AbilityUseType useType;

    @Override
    public int compareTo(Ability o) {
        return Integer.compare(this.priority, o.priority);
    }
}
