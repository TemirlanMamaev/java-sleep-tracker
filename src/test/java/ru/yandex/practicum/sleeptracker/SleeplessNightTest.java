package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SleeplessNightTest {

    private final SleeplessNight sleeplessNight = new SleeplessNight();

    @Test
    void shouldReturnZeroForEmptyList() {
        SleepAnalysisResult<?> result = sleeplessNight.apply(List.of());

        assertEquals(0L, result.getOutcome());
    }

    @Test
    void shouldReturnZeroWhenThereWasSleepAtNight() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        QualityOfSleep.GOOD
                )
        );

        SleepAnalysisResult<?> result = sleeplessNight.apply(sessions);

        assertEquals(0L, result.getOutcome());
    }

    @Test
    void shouldReturnOneWhenThereWasNoSleepAtNight() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 7, 0),
                        LocalDateTime.of(2025, 10, 1, 11, 0),
                        QualityOfSleep.GOOD
                )
        );

        SleepAnalysisResult<?> result = sleeplessNight.apply(sessions);

        assertEquals(1L, result.getOutcome());
    }

    @Test
    void shouldNotCountNightAsSleeplessWhenSleepWasFrom23To3() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 0),
                        LocalDateTime.of(2025, 10, 2, 3, 0),
                        QualityOfSleep.GOOD
                )
        );

        SleepAnalysisResult<?> result = sleeplessNight.apply(sessions);

        assertEquals(0L, result.getOutcome());
    }

    @Test
    void shouldNotCountNightAsSleeplessWhenSleepWasFrom2To7() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 2, 0),
                        LocalDateTime.of(2025, 10, 2, 7, 0),
                        QualityOfSleep.GOOD
                )
        );

        SleepAnalysisResult<?> result = sleeplessNight.apply(sessions);

        assertEquals(0L, result.getOutcome());
    }
}