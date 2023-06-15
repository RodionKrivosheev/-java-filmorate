package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.RatingStorage;
import ru.yandex.practicum.filmorate.model.FilmRating;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingDbService {
    private RatingStorage ratingStorage;

    public List<FilmRating> getRatingList() {
        return ratingStorage.getRatingList();
    }

    public FilmRating getRatingById(int ratingId) {
        return ratingStorage.getRatingById(ratingId);
    }
}
