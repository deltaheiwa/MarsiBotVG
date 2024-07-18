package com.marsi.util;

import java.time.OffsetDateTime;

public class TimestampUtil {
    public static String getRelativeTimestamp(OffsetDateTime time) {
        long timestamp = time.toEpochSecond();
        return String.format("<t:%d:R>", timestamp);
    }
}
