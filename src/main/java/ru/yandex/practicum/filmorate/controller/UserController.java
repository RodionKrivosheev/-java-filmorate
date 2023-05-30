package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        log.info("add user complete");
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("update user complete");
        users.put(user.getId(), user);
        return user;
    }

    @GetMapping
    public List<User> getAllUsers() {
        log.info("get all users complete");
        return new ArrayList<>(users.values());
    }
}