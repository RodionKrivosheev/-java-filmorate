package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ValidationException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeDbService {
    private final LikeStorage likeStorage;
    private final GenreStorage genreStorage;
    private final RatingStorage ratingStorage;

    private final UserStorage userStorage;
    private final FilmStorage filmStorage;

    public void like(int filmId, int userId) {
        checkExistenceOfUserAndFilm(filmId, userId);

        likeStorage.like(userId, filmId);
    }

    public void unlike(int filmId, int userId) {
        if (checkExistenceOfUserAndFilm(filmId, userId)) {
            likeStorage.unlike(userId, filmId);
        } else {
            throw new ValidationException("Problem with existence of user " + userId + " or film " + filmId);
        }
    }

    public List<Film> getMostPopularFilms(Integer filmsCount) {
        return likeStorage.getMostPopularFilms(filmsCount);
    }

    private boolean checkExistenceOfUserAndFilm(int filmId, int userId) {
        if (userStorage.getUserById(userId) != null && filmStorage.getFilmById(filmId) != null) {
            return true;
        } else {
            return false;
        }
    }
}
