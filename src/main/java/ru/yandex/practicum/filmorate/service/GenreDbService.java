package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.impl.GenreStorage;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreDbService {
    private final GenreStorage genreStorage;

    public List<Genre> getGenresList() {
        return genreStorage.getGenresList();
    }

    public Genre getGenreById(int genreId) {
        return genreStorage.getGenreById(genreId);
    }
}
