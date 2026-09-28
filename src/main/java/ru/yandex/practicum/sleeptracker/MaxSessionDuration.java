package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class MaxSessionDuration implements Function<List<SleepingSession>, SleepAnalysisResult<?>> {
    @Override
    public SleepAnalysisResult<?> apply(List<SleepingSession> sessions) {
        long duration = sessions.stream()
                .mapToLong(session -> java.time.Duration.between(
                        session.getStartOfSleep(),
                        session.getEndOfSleep()
                ).toMinutes())
                .max()
                .orElse(-1L);
        return new SleepAnalysisResult<>("Максимальная сессия сна", duration);
    }
}
