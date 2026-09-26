package ru.yandex.practicum.sleeptracker;

import java.util.List;
import java.util.function.Function;

public class TotalSessions implements Function<List<SleepingSession>, SleepAnalysisResult<?>> {
    @Override
    public SleepAnalysisResult<?> apply(List<SleepingSession> sessions) {
        long quantity = sessions.stream()
                .count();
        return new SleepAnalysisResult<>("Всего сессий сна", quantity);
    }
}