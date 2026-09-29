package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;
import java.time.Duration;

public class SleepingSession {
    private final LocalDateTime startOfSleep;
    private final LocalDateTime endOfSleep;
    private final QualityOfSleep quality;

    public SleepingSession(LocalDateTime startOfSleep, LocalDateTime endOfSleep, QualityOfSleep quality) {
        this.startOfSleep = startOfSleep;
        this.endOfSleep = endOfSleep;
        this.quality = quality;
    }

    public LocalDateTime getStartOfSleep() {
        return startOfSleep;
    }

    public LocalDateTime getEndOfSleep() {
        return endOfSleep;
    }

    public QualityOfSleep getQuality() {
        return quality;
    }

    public long getDurationInMinutes() {
        return Duration.between(startOfSleep, endOfSleep).toMinutes();
    }
}