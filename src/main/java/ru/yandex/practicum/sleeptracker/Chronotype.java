package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Chronotype implements Function<List<SleepingSession>, SleepAnalysisResult<?>> {

    @Override
    public SleepAnalysisResult<?> apply(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return new SleepAnalysisResult<>("Хронотип пользователя", ChronotypeType.PIGEON);
        }

        List<SleepingSession> nightSessions = sessions.stream()
                .filter(session -> isNightSession(session))
                .toList();

        Map<LocalDate, List<SleepingSession>> nights = nightSessions.stream()
                .collect(Collectors.groupingBy(session -> getNightDate(session)));

        List<SleepingSession> combinedNights = nights.values().stream()
                .map(this::combineNightSessions)
                .toList();

        long owlCount = combinedNights.stream()
                .filter(session -> isOwl(session))
                .count();

        long larkCount = combinedNights.stream()
                .filter(session -> isLark(session))
                .count();

        long pigeonCount = combinedNights.size() - owlCount - larkCount;

        if (owlCount > larkCount && owlCount > pigeonCount) {
            return new SleepAnalysisResult<>("Хронотип пользователя", ChronotypeType.OWL);
        }

        if (larkCount > owlCount && larkCount > pigeonCount) {
            return new SleepAnalysisResult<>("Хронотип пользователя", ChronotypeType.LARK);
        }

        return new SleepAnalysisResult<>("Хронотип пользователя", ChronotypeType.PIGEON);
    }

    private boolean isNightSession(SleepingSession session) {
        LocalTime startTime = session.getStartOfSleep().toLocalTime();

        return startTime.isAfter(LocalTime.of(18, 0))
                || startTime.isBefore(LocalTime.of(7, 0));
    }

    private LocalDate getNightDate(SleepingSession session) {
        LocalDate date = session.getStartOfSleep().toLocalDate();
        LocalTime startTime = session.getStartOfSleep().toLocalTime();

        if (startTime.isBefore(LocalTime.of(7, 0))) {
            return date.minusDays(1);
        }

        return date;
    }

    private SleepingSession combineNightSessions(List<SleepingSession> sessions) {
        LocalDateTime start = sessions.stream()
                .map(session -> session.getStartOfSleep())
                .min(LocalDateTime::compareTo)
                .orElseThrow();

        LocalDateTime end = sessions.stream()
                .map(session -> session.getEndOfSleep())
                .max(LocalDateTime::compareTo)
                .orElseThrow();

        return new SleepingSession(
                start,
                end,
                QualityOfSleep.GOOD
        );
    }

    private boolean isOwl(SleepingSession session) {
        LocalTime startTime = session.getStartOfSleep().toLocalTime();
        LocalTime endTime = session.getEndOfSleep().toLocalTime();

        boolean lateStart = startTime.isAfter(LocalTime.of(23, 0))
                || startTime.isBefore(LocalTime.of(7, 0));

        boolean lateWakeUp = endTime.isAfter(LocalTime.of(9, 0));

        return lateStart && lateWakeUp;
    }

    private boolean isLark(SleepingSession session) {
        LocalTime startTime = session.getStartOfSleep().toLocalTime();
        LocalTime endTime = session.getEndOfSleep().toLocalTime();

        boolean earlyStart = startTime.isBefore(LocalTime.of(22, 0))
                && startTime.isAfter(LocalTime.of(7, 0));

        boolean earlyWakeUp = endTime.isBefore(LocalTime.of(7, 0));

        return earlyStart && earlyWakeUp;
    }
}
