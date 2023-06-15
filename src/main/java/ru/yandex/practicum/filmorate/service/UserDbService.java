package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.impl.UserStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Service
@Qualifier
public class UserDbService {
    private final UserStorage userStorage;

    public UserDbService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User addUser(User user) {
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public void addFriend(int userId, int friendId) {
        userStorage.addFriend(userId, friendId);
    }

    public User getUserById(int userId) {
        return userStorage.getUserById(userId);
    }

    public List<User> getFriends(int userId) {
        return userStorage.getFriends(userId);
    }

    public List<User> getCommonFriends(int userId, int friendId) {
        return userStorage.getCommonFriends(userId, friendId);
    }

    public List<User> getUsersList(int max) {
        return userStorage.getUsersList(max);
    }

    public void deleteFriend(int userId, int friendId) {
        userStorage.deleteFriend(userId, friendId);
    }
}
