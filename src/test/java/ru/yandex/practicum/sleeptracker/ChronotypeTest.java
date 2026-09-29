package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChronotypeTest {

    private final Chronotype chronotype = new Chronotype();

    @Test
    void shouldReturnDoveForEmptyList() {
        SleepAnalysisResult<?> result = chronotype.apply(List.of());

        assertEquals(ChronotypeType.PIGEON, result.getOutcome());
    }

    @Test
    void shouldReturnOwlWhenOwlSessionsAreMoreCommon() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 10, 0),
                        QualityOfSleep.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 23, 30),
                        LocalDateTime.of(2025, 10, 3, 10, 0),
                        QualityOfSleep.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 21, 0),
                        LocalDateTime.of(2025, 10, 4, 6, 0),
                        QualityOfSleep.GOOD
                )
        );

        SleepAnalysisResult<?> result = chronotype.apply(sessions);

        assertEquals(ChronotypeType.OWL, result.getOutcome());
    }

    @Test
    void shouldReturnLarkWhenLarkSessionsAreMoreCommon() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 21, 0),
                        LocalDateTime.of(2025, 10, 2, 6, 0),
                        QualityOfSleep.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 21, 30),
                        LocalDateTime.of(2025, 10, 3, 6, 30),
                        QualityOfSleep.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 3, 23, 30),
                        LocalDateTime.of(2025, 10, 4, 10, 0),
                        QualityOfSleep.GOOD
                )
        );

        SleepAnalysisResult<?> result = chronotype.apply(sessions);

        assertEquals(ChronotypeType.LARK, result.getOutcome());
    }

    @Test
    void shouldReturnPigeonWhenTypesAreEqual() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 10, 0),
                        QualityOfSleep.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 21, 0),
                        LocalDateTime.of(2025, 10, 3, 6, 0),
                        QualityOfSleep.GOOD
                )
        );

        SleepAnalysisResult<?> result = chronotype.apply(sessions);

        assertEquals(ChronotypeType.PIGEON, result.getOutcome());
    }

    @Test
    void shouldIgnoreDaytimeSleep() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 13, 0),
                        LocalDateTime.of(2025, 10, 1, 15, 0),
                        QualityOfSleep.GOOD
                )
        );

        SleepAnalysisResult<?> result = chronotype.apply(sessions);

        assertEquals(ChronotypeType.PIGEON, result.getOutcome());
    }

    @Test
    void shouldRecognizeOwlWhenSleepStartsAfterMidnight() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 0, 30),
                        LocalDateTime.of(2025, 10, 1, 10, 0),
                        QualityOfSleep.GOOD
                )
        );

        SleepAnalysisResult<?> result = chronotype.apply(sessions);

        assertEquals(ChronotypeType.OWL, result.getOutcome());
    }

    @Test
    void shouldIgnoreMorningSession() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 8, 0),
                        LocalDateTime.of(2025, 10, 1, 11, 0),
                        QualityOfSleep.GOOD
                )
        );

        SleepAnalysisResult<?> result = chronotype.apply(sessions);

        assertEquals(ChronotypeType.PIGEON, result.getOutcome());
    }

    @Test
    void shouldNotRecognizeLarkWhenSleepStartsAfterMidnight() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 5, 0, 10),
                        LocalDateTime.of(2025, 10, 5, 6, 20),
                        QualityOfSleep.GOOD
                )
        );

        SleepAnalysisResult<?> result = chronotype.apply(sessions);

        assertEquals(ChronotypeType.PIGEON, result.getOutcome());
    }

    @Test
    void shouldTreatSeveralSessionsAsOneNight() {
        List<SleepingSession> sessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 1, 23, 30),
                        LocalDateTime.of(2025, 10, 2, 2, 0),
                        QualityOfSleep.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 10, 2, 2, 30),
                        LocalDateTime.of(2025, 10, 2, 10, 0),
                        QualityOfSleep.GOOD
                )
        );

        SleepAnalysisResult<?> result = chronotype.apply(sessions);

        assertEquals(ChronotypeType.OWL, result.getOutcome());
    }
}
