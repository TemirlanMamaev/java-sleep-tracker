package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MinSessionDurationTest {

    private final MinSessionDuration minSessionDuration = new MinSessionDuration();

    @Test
    void shouldReturnDurationWhenThereIsOneSession() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        QualityOfSleep.GOOD
                )
        );

        SleepAnalysisResult<?> result = minSessionDuration.apply(sessions);

        assertEquals(480L, result.getOutcome());
    }

    @Test
    void shouldReturnMinimumDuration() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        QualityOfSleep.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 5, 30),
                        QualityOfSleep.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 21, 0),
                        LocalDateTime.of(2025, 10, 4, 7, 0),
                        QualityOfSleep.GOOD
                )
        );

        SleepAnalysisResult<?> result = minSessionDuration.apply(sessions);

        assertEquals(390L, result.getOutcome());
    }
}