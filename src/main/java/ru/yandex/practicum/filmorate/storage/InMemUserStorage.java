package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.service.Validator;


import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@Slf4j
public class InMemUserStorage implements UserStorage {
    private static HashMap<Integer, User> users = new HashMap<>();

    public static HashMap<Integer, User> getUsers() {
        return users;
    }

    UserService userService;
    private int id = 1;

    private int generateId() {
        return id++;
    }

    @Override
    public User addUser(User user) {
        log.debug("add user");
        Validator.validateUser(user);
        user.setId(generateId());
        users.put(user.getId(), user);
        log.debug("Пользователь сохранен.");
        return user;
    }

    @Override
    public User updateUser(User user) {
        log.debug("update user");
        Validator.validateUser(user);
        Validator.isExist(user);
        users.put(user.getId(), user);
        log.debug("Данные пользователя с ID " + user.getId() + " обновлены.");
        return user;


    }

    @Override
    public List<User> getAllUsers() {
        log.debug("get all users");
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUserById(int userId) {
        if (users.containsKey(userId)) {
            return users.get(userId);
        } else {
            throw new NotFoundException("Пользователь не найден!");
        }
    }

}
