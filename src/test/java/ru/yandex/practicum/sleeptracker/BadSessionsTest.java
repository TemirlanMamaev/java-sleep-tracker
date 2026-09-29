package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BadSessionsTest {

    private final BadSessions badSessions = new BadSessions();

    @Test
    void shouldReturnZeroWhenThereAreNoBadSessions() {
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
                )
        );

        SleepAnalysisResult<?> result = badSessions.apply(sessions);

        assertEquals(0L, result.getOutcome());
    }

    @Test
    void shouldReturnNumberOfBadSessions() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 22, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        QualityOfSleep.BAD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 0),
                        LocalDateTime.of(2025, 10, 3, 7, 0),
                        QualityOfSleep.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 21, 0),
                        LocalDateTime.of(2025, 10, 4, 6, 0),
                        QualityOfSleep.BAD
                )
        );

        SleepAnalysisResult<?> result = badSessions.apply(sessions);

        assertEquals(2L, result.getOutcome());
    }
}