package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreStorage {
    List<Genre> getGenresList();

    List<Genre> getGenresListForFilm(int filmId);

    List<Genre> addGenresToFilm(int filmId, List<Genre> genres);

    Genre getGenreById(int genreId);

    void deleteGenresForFilm(int filmId);
}
