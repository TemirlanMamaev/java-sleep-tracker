package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class MinSessionDuration implements Function<List<SleepingSession>, SleepAnalysisResult<?>> {
    @Override
    public SleepAnalysisResult<?> apply(List<SleepingSession> sessions) {
        long duration = sessions.stream()
                .mapToLong(session -> session.getDurationInMinutes())
                .min()
                .orElse(-1L);

        return new SleepAnalysisResult<>("Минимальная продолжительность сессии сна, мин.", duration);
    }
}
