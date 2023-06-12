package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController extends AbstractController<User, UserService> {
    @Autowired
    public UserController(UserService service) {
        super(service);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public User addFriend(@PathVariable("userId") int userId, @PathVariable("friendId") int friendId) {
        log.info("User " + userId + " add Friend: " + friendId);
        return service.addFriend(userId, friendId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public User deleteFriend(@PathVariable("userId") int userId, @PathVariable("friendId") int friendId) {
        log.info("User " + userId + " delete Friend: " + friendId);
        return service.deleteFriend(userId, friendId);
    }

    @GetMapping("/{userId}/friends")
    public List<User> getFriends(@PathVariable int userId) {
        log.info("User " + userId + " get Friends");
        return service.getFriends(userId);
    }

    @GetMapping("/{userId}/friends/common/{otherId}")
    public List<User> getCorporateFriends(@PathVariable int userId, @PathVariable int otherId) {
        log.info("User " + userId + " get Corporate Friends with User " + otherId);
        return service.corporateFriends(userId, otherId);
    }

}