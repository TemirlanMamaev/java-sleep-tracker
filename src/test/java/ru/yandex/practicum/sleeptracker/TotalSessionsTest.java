package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TotalSessionsTest {

    private final TotalSessions totalSessions = new TotalSessions();

    @Test
    void shouldReturnZeroForEmptyList() {
        SleepAnalysisResult<?> result = totalSessions.apply(List.of());

        assertEquals(0L, result.getOutcome());
    }

    @Test
    void shouldReturnNumberOfSessions() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        QualityOfSleep.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        QualityOfSleep.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 21, 0),
                        LocalDateTime.of(2025, 10, 4, 6, 0),
                        QualityOfSleep.BAD
                )
        );

        SleepAnalysisResult<?> result = totalSessions.apply(sessions);

        assertEquals(3L, result.getOutcome());
    }
}