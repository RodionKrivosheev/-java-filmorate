package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmStorage;
import ru.yandex.practicum.filmorate.dao.LikeStorage;
import ru.yandex.practicum.filmorate.dao.UserStorage;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeStorage likeStorage;
    private final UserStorage userStorage;
    private final FilmStorage filmStorage;

    public void like(int filmId, int userId) {
        checkExistenceOfUserAndFilm(filmId, userId);

        likeStorage.like(userId, filmId);
    }

    public void unlike(int filmId, int userId) {
        checkExistenceOfUserAndFilm(filmId, userId);

        likeStorage.unlike(userId, filmId);
    }

    public List<Film> getMostPopularFilms(Integer filmsCount) {
        return likeStorage.getMostPopularFilms(filmsCount);
    }

    private void checkExistenceOfUserAndFilm(int filmId, int userId) {
        userStorage.getUserById(userId);
        filmStorage.getFilmById(filmId);
    }
}
