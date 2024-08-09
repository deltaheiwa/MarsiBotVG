package com.marsi.vg.events;

import java.util.EventObject;

public class PhaseChangeEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public PhaseChangeEvent(Object source) {
        super(source);
    }
}
