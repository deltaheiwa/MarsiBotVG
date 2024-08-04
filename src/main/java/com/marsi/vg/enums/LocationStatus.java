package com.marsi.vg.enums;

public enum LocationStatus {
    OPENED(1),
    CLOSED(2);

    private final int value;

    LocationStatus(int value) {
        this.value = value;
    }

    public int asDatabaseId() {
        return value;
    }

    public static LocationStatus fromDatabaseId(int id) {
        for (LocationStatus status : values()) {
            if (status.asDatabaseId() == id) {
                return status;
            }
        }
        return null;
    }
}
