package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class BadSessions implements Function<List<SleepingSession>, SleepAnalysisResult<?>> {
    @Override
    public SleepAnalysisResult<?> apply(List<SleepingSession> sessions) {
        long quantity = sessions.stream()
                .filter(session -> session.getQuality() == QualityOfSleep.BAD)
                .count();
        return new SleepAnalysisResult<>("Всего плохих сессий сна", quantity);
    }
}