package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ValidationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@Slf4j
public class InMemUserStorage implements UserStorage {
    private HashMap<Integer, User> users = new HashMap<>();
    private int id = 1;

    private int generateId() {
        return id++;
    }

    @Override
    public User addUser(User user) {
        log.info("add user");
        validateUser(user);
        user.setId(generateId());
        users.put(user.getId(), user);
        log.info("Пользователь сохранен.");
        return user;
    }

    @Override
    public User updateUser(User user) {
        log.info("update user");
        validateUser(user);
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException("Пользователь не найден.");
        }
        users.put(user.getId(), user);
        log.info("Данные пользователя с ID " + user.getId() + " обновлены.");
        return user;


    }

    @Override
    public List<User> getAllUsers() {
        log.info("get all users");
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

    public void validateUser(User user) {
        if (user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин не может быть пустым и содержать пробелы.");
        }
        if ((user.getName() == null) || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
