package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.FilmRating;

import java.util.List;

public interface RatingStorageIn {
    List<FilmRating> getRatingList();

    FilmRating getRatingById(int ratingId);
}
