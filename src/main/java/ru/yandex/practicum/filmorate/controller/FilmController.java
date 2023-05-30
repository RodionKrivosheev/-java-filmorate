package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/film")
public class FilmController {
    private final LocalDate MIN_DATE = LocalDate.of(1895, 12, 28);
    private final Map<Integer, Film> films = new HashMap<>();


    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        validate(film);
        log.info("add film complete");
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        validate(film);
        log.info("update fill complete");
        films.put(film.getId(), film);
        return film;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        log.info("get all films complete");
        return new ArrayList<>(films.values());
    }

    private void validate(Film film) {
        if (film.getDateReliese().isBefore(MIN_DATE)) {
            throw new ValidationException("Дата релиза должна быть < 28 декабря 1895 года!");
        }
    }
}
