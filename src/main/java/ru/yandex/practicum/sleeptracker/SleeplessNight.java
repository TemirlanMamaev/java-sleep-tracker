package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;

public class SleeplessNight implements Function<List<SleepingSession>, SleepAnalysisResult<?>> {
    @Override
    public SleepAnalysisResult<?> apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult<>("Количество бессонных ночей", 0L);
        }

        SleepingSession first = sessions.getFirst();
        SleepingSession last = sessions.getLast();
        LocalDate firstDate = first.getStartOfSleep().toLocalDate();
        LocalTime firstTime = first.getStartOfSleep().toLocalTime();
        LocalDate startDate;
        LocalDate endDate = last.getEndOfSleep().toLocalDate();
        if (firstTime.isBefore(LocalTime.of(12, 0))) {
            startDate = firstDate.minusDays(1);
        } else {
            startDate = firstDate;
        }
        long sleeplessCount = java.util.stream.Stream.iterate(startDate, date -> date.plusDays(1))
                .limit(java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate))
                .filter(nightDate -> {
                    LocalDateTime nightStart = nightDate.plusDays(1).atStartOfDay();
                    LocalDateTime nightEnd = nightDate.plusDays(1).atTime(6, 0);
                    boolean hasSleep = sessions.stream().anyMatch(session -> {
                        LocalDateTime sessionStart = session.getStartOfSleep();
                        LocalDateTime sessionEnd = session.getEndOfSleep();
                        return sessionStart.isBefore(nightEnd) && sessionEnd.isAfter(nightStart);
                    });
                    return !hasSleep;
                }).count();
        return new SleepAnalysisResult<>("Количество бессонных ночей", sleeplessCount);
    }
}
