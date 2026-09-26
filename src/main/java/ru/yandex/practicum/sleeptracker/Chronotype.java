package ru.yandex.practicum.sleeptracker;

import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;

public class Chronotype implements Function<List<SleepingSession>, SleepAnalysisResult<?>> {

    @Override
    public SleepAnalysisResult<?> apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult<>("Хронотип пользователя", "голубь");
        }

        List<SleepingSession> nightSessions = sessions.stream()
                .filter(session -> {
                    LocalTime startTime = session.getStartOfSleep().toLocalTime();
                    LocalTime endTime = session.getEndOfSleep().toLocalTime();

                    return startTime.isAfter(LocalTime.of(18, 0))
                            || endTime.isBefore(LocalTime.of(12, 0));
                })
                .toList();

        long owlCount = nightSessions.stream()
                .filter(session -> {
                    LocalTime startTime = session.getStartOfSleep().toLocalTime();
                    LocalTime endTime = session.getEndOfSleep().toLocalTime();

                    return startTime.isAfter(LocalTime.of(23, 0))
                            && endTime.isAfter(LocalTime.of(9, 0));
                })
                .count();

        long larkCount = nightSessions.stream()
                .filter(session -> {
                    LocalTime startTime = session.getStartOfSleep().toLocalTime();
                    LocalTime endTime = session.getEndOfSleep().toLocalTime();

                    return startTime.isBefore(LocalTime.of(22, 0))
                            && endTime.isBefore(LocalTime.of(7, 0));
                })
                .count();

        long pigeonCount = nightSessions.size() - owlCount - larkCount;

        if (owlCount > larkCount && owlCount > pigeonCount) {
            return new SleepAnalysisResult<>("Хронотип пользователя", "сова");
        }

        if (larkCount > owlCount && larkCount > pigeonCount) {
            return new SleepAnalysisResult<>("Хронотип пользователя", "жаворонок");
        }

        return new SleepAnalysisResult<>("Хронотип пользователя", "голубь");
    }
}