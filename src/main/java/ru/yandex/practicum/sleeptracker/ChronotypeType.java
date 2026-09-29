package ru.yandex.practicum.sleeptracker;

public enum ChronotypeType {
    OWL("сова"),
    LARK("жаворонок"),
    PIGEON("голубь");

    private final String value;

    ChronotypeType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
