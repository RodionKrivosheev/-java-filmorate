package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {
    @Autowired
    private UserController userController;
    private User user;
    private User user1;

    @BeforeEach
    public void beforeEach() {
        user = new User(0,"email1@gmail.com", "Login1", "ddd", LocalDate.of(1994, 11, 02));
        user.setName("Name1");
        user1 = new User(1,"email2@gmail.com","Login2", "ddd", LocalDate.of(1994, 11, 02));
        user.setName("Name2");
        userController.getAllUsers().clear();
    }

    @Test
    public void addUser() {
        userController.addUser(user);
        assertEquals(userController.getAllUsers().get(0), user);
        assertEquals(userController.getAllUsers().size(), 1);
    }

    @Test
    public void updateUser() {
        userController.addUser(user);
        userController.addUser(user1);
        user1 = new User(1,"email2@gmail.com","Login2", "ddd", LocalDate.of(1992, 11, 02));
        user1.setId(2);
        userController.updateUser(user1);
        assertEquals(userController.getAllUsers().get(1), user1);
    }
}