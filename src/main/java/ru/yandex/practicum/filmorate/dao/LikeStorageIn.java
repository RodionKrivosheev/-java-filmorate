package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface LikeStorageIn {
    void like(int userId, int filmId);

    void unlike(int userId, int filmId);

    List<Film> getMostPopularFilms(Integer filmsCount);
}
