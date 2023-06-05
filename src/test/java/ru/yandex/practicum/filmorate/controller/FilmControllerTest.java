package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ValidationException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FilmControllerTest {
    @Autowired
    private FilmController filmController;
    private Film film;
    private Film film2;

    @BeforeEach
    public void beforeEach() {
        film = new Film("Film1", "Description1", LocalDate.of(1994, 11, 02), 190);
        film2 = new Film("Film2", "Description2", LocalDate.of(2010, 9, 03), 190);

    }

    @Test
    public void addFilm() {
        filmController.addFilm(film);
        assertEquals(filmController.getAllFilms().get(0), film);
        assertEquals(filmController.getAllFilms().size(), 1);
    }

    @Test
    public void updateFilm() {
        filmController.addFilm(film);
        filmController.addFilm(film2);
        film2 = new Film("Film3", "Description3", LocalDate.of(1555, 7, 15), 190);
        ValidationException e = assertThrows(ValidationException.class, () -> filmController.updateFilm(film2));
        assertEquals(e.getMessage(), "Дата релиза должна быть не раньше 28 декабря 1895 года!");
    }

}