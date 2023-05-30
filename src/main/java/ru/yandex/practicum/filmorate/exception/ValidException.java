package ru.yandex.practicum.filmorate.exception;

public static class ValidException extends RuntimeException {
    public ValidException(String message) {
        super(message);
    }
}