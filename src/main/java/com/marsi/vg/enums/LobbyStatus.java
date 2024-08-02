package com.marsi.vg.enums;

public enum LobbyStatus {
    OPEN(1),
    IN_PROGRESS(2),
    CLOSED(3);

    private final int value;

    LobbyStatus(int value) {
        this.value = value;
    }

    public int asDatabaseId() {
        return value;
    }

    public static LobbyStatus fromDatabaseId(int id) {
        for (LobbyStatus status : values()) {
            if (status.asDatabaseId() == id) {
                return status;
            }
        }
        return null;
    }
}
