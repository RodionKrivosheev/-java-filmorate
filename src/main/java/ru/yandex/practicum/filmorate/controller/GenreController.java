package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreDbService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Component
@RequestMapping("/genres")
public class GenreController {
    private final GenreDbService genreDbService;

    @GetMapping
    public List<Genre> getGenresList() {
        log.info("Get GenresList");
        return genreDbService.getGenresList();
    }

    @GetMapping("/{id}")
    public Genre getGenreById(@PathVariable int id) {
        log.info("Get Genres by id");
        return genreDbService.getGenreById(id);
    }
}
