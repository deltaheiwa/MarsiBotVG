package com.marsi.vg.events.listeners;

import com.marsi.vg.events.PhaseChangeEvent;

public interface PhaseChangeListener extends CoreListener {
    void onPhaseChange(PhaseChangeEvent event);
}
