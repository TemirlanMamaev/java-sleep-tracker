package ru.yandex.practicum.sleeptracker;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class SleepTrackerApp {

    private static final List<Function<List<SleepingSession>, SleepAnalysisResult<?>>> functions = List.of(
            new TotalSessions(),
            new BadSessions(),
            new MinSessionDuration(),
            new MaxSessionDuration(),
            new AverageSessionDuration(),
            new SleeplessNight(),
            new Chronotype()
    );

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Укажите путь к файлу.");
            return;
        }

        try {
            List<SleepingSession> sessions = readSessions(args[0]);
            functions.stream()
                    .map(function -> function.apply(sessions))
                    .forEach(result ->
                            System.out.println(result.getDescription() + ": " + result.getOutcome())
                    );

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static List<SleepingSession> readSessions(String filePath) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

        try (Stream<String> lines = Files.lines(Path.of(filePath))) {
            return lines
                    .filter(line -> !line.isBlank())
                    .map(line -> {
                        String[] parts = line.split(";");

                        LocalDateTime start = LocalDateTime.parse(parts[0].trim(), formatter);
                        LocalDateTime end = LocalDateTime.parse(parts[1].trim(), formatter);
                        QualityOfSleep quality = QualityOfSleep.valueOf(parts[2].trim());

                        return new SleepingSession(start, end, quality);
                    })
                    .toList();
        }
    }
}