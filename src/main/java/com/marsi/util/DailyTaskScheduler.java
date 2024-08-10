package com.marsi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DailyTaskScheduler {
    private final Logger logger = LoggerFactory.getLogger(DailyTaskScheduler.class);
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void startTask(Runnable task, int targetHour) {
        long initialDelay = calculateInitialDelay(targetHour);
        long period = 24 * 60 * 60; // 24 hours in seconds

        scheduler.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
    }

    private long calculateInitialDelay(int targetHour) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextRun = now.withHour(targetHour).withMinute(0).withSecond(0).withNano(0);

        if (now.isAfter(nextRun)) {
            nextRun = nextRun.plusDays(1); // If target hour has already passed today, schedule for tomorrow
        }

        logger.info("Next run scheduled for: {}", nextRun);
        return ChronoUnit.SECONDS.between(now, nextRun);
    }

    public void stopTask() {
        scheduler.shutdown();
    }
}
