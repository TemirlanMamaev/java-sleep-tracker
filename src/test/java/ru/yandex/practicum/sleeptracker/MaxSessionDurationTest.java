package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaxSessionDurationTest {

    private final MaxSessionDuration maxSessionDuration = new MaxSessionDuration();

    @Test
    void shouldReturnDurationWhenThereIsOneSession() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        QualityOfSleep.GOOD
                )
        );

        SleepAnalysisResult<?> result = maxSessionDuration.apply(sessions);

        assertEquals(480L, result.getOutcome());
    }

    @Test
    void shouldReturnMaximumDuration() {
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

        SleepAnalysisResult<?> result = maxSessionDuration.apply(sessions);

        assertEquals(600L, result.getOutcome());
    }

    @Test
    void shouldReturnMinusOneForEmptyList() {
        SleepAnalysisResult<?> result = maxSessionDuration.apply(List.of());
        assertEquals(-1L, result.getOutcome());
    }
}