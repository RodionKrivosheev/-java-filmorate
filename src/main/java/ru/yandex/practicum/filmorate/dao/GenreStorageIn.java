package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreStorageIn {
    List<Genre> getGenresList();

    List<Genre> getGenresListForFilm(int filmId);

    Genre getGenreById(int genreId);
}
