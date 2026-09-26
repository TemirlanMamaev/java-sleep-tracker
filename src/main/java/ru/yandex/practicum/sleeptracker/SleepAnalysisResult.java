package ru.yandex.practicum.sleeptracker;

public class SleepAnalysisResult<T> {
    private final String description;
    private final T outcome;

    public SleepAnalysisResult(String description, T outcome) {
        this.description = description;
        this.outcome = outcome;
    }

    public String getDescription() {
        return description;
    }

    public T getOutcome() {
        return outcome;
    }
}