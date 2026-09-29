package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class AverageSessionDuration implements Function<List<SleepingSession>, SleepAnalysisResult<?>> {
    @Override
    public SleepAnalysisResult<?> apply(List<SleepingSession> sessions) {
        long duration = Math.round(
                sessions.stream()
                        .mapToLong(session -> session.getDurationInMinutes())
                        .average()
                        .orElse(-1.0)
        );

        return new SleepAnalysisResult<>("Средняя продолжительность сессии сна, мин.", duration);
    }
}
