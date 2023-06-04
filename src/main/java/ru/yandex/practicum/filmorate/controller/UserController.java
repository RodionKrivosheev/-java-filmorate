package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    private HashMap<Integer, User> users = new HashMap<>();
    private int id = 1;

    private int generateId() {
        return id++;
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        log.info("add user");
        validateUser(user);
        user.setId(generateId());
        users.put(user.getId(), user);
        log.info("Пользователь сохранен.");
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("update user");
        validateUser(user);
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("Пользователь не найден.");
        }
        users.put(user.getId(), user);
        log.info("Данные пользователя с ID " + user.getId() + " обновлены.");
        return user;


    }

    @GetMapping
    public List<User> getAllUsers() {
        log.info("get all users");
        return new ArrayList<>(users.values());
    }

    public static void validateUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @.");
        }
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы.");
        }
        if ((user.getName() == null) || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday() != null && user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем.");
        }
    }
}