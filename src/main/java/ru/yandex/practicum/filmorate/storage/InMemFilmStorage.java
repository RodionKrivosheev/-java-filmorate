package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemFilmStorage implements FilmStorage {
    private int id = 1;
    private final Map<Integer, Film> films = new HashMap<>();

    private int generateId() {
        return id++;
    }


    @Override
    public Film addFilm(Film film) {
        validate(film);
        log.debug("add film");
        film.setId(generateId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        validate(film);
        if (!films.containsKey(film.getId())) {
            throw new NotFoundException("Фильм не найден.");
        }
        log.debug("update film");
        films.put(film.getId(), film);
        return film;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        log.debug("get all films");
        return new ArrayList<>(films.values());
    }

    @Override
    public Film getFilmById(int id) {
        if (films.containsKey(id)) {
            return films.get(id);
        } else {
            throw new NotFoundException("Фильм не найден!");
        }
    }

    private void validate(Film film) {
        if (film.getReleaseDate().isBefore(Constants.MIN_DATE)) {
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895 года!");
        }
        if (film.getDescription() != null && film.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длина описания — 200 символов.");
        }
    }
}
