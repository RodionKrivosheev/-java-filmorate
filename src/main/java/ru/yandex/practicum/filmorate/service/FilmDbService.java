package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmStorage;
import ru.yandex.practicum.filmorate.dao.GenreStorage;
import ru.yandex.practicum.filmorate.dao.RatingStorage;
import ru.yandex.practicum.filmorate.dao.impl.GenreDbStorage;
import ru.yandex.practicum.filmorate.dao.impl.RatingDbStorage;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Service
@Qualifier
@RequiredArgsConstructor
public class FilmDbService {
    public static GenreStorage genreStorage;
    public static RatingStorage ratingStorage;
    private final FilmStorage filmStorage;


    public Film addFilm(Film film) {
        film.setMpa(ratingStorage.getRatingById(film.getMpa().getId()));
        return filmStorage.addFilm(film);
    }

    public List<Film> getFilmsList(int max) {
        return filmStorage.getFilmsList(max);
    }

    public Film getFilmById(int filmId) {
        return filmStorage.getFilmById(filmId);
    }

    public Film updateFilm(Film film) {
        film.setMpa(ratingStorage.getRatingById(film.getMpa().getId()));
        return filmStorage.updateFilm(film);
    }
}
