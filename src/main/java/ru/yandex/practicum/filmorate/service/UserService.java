package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService extends AbstractService<User, UserStorage> {
    public UserService(UserStorage storage) {
        super(storage);
    }

    @Override
    public User add(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        user = super.add(user);
        log.info("Добавлен пользователь {}", user);

        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = super.findAll();
        users.forEach(storage::loadFriends);
        return users;
    }

    @Override
    public User findById(int id) {
        User user = super.findById(id);
        storage.loadFriends(user);
        return user;
    }

    //Шаблонный метод
    @Override
    public void validationBeforeCreate(User user) {
        if (super.storage.containsEmail(user.getEmail())) {
            String message = ("Пользователь с электронной почтой " +
                    user.getEmail() + " уже зарегистрирован.");
            log.warn(message);
            throw new UserAlreadyExistException(message);
        }
    }

    public User addFriend(int userId, int friendId) {
        User user = storage.findById(userId);
        Validator.validateUser(user);
        User friend = storage.findById(friendId);
        user.addFriend(friendId);
        friend.addFriend(userId);
        log.info("Пользователь " + user.getName() + " добавлен в список друзей " + friend.getName());
        return user;
    }

    public User deleteFriend(int userId, int friendId) {
        User user = storage.findById(userId);
        Validator.validateUser(user);
        User friend = storage.findById(friendId);
        user.deleteFriend(friendId);
        friend.deleteFriend(userId);
        log.info("Пользователь " + user.getName() + " удален из списка друзей " + friend.getName());
        return user;
    }

    public List<User> getFriends(int userId) {
        User user = storage.findById(userId);
        Validator.validateUser(user);
        List<User> friendsList = new ArrayList<>();
        for (Integer id : user.getFriends()) {
            friendsList.add(storage.findById(id));
        }
        log.info("Список друзей пользователя " + user.getName());
        return friendsList;
    }

    public List<User> corporateFriends(int userId, int friendId) {
        User user = storage.findById(userId);
        Validator.validateUser(user);
        User friend = storage.findById(friendId);
        List<User> mutualFriends = new ArrayList<>();
        for (Integer id : user.getFriends()) {
            if (friend.getFriends().contains(id)) {
                User mutualFriend = storage.findById(id);
                mutualFriends.add(mutualFriend);
            }
        }
        log.info("Список общих друзей пользователей");
        return mutualFriends;
    }
}
