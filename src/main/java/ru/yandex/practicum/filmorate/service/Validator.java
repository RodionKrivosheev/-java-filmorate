package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ValidationException;

public class Validator {
    public static void validateUser(User user) {
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы.");
        }
        if ((user.getName() == null) || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
