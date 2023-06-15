package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmDbService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@Component
@RequiredArgsConstructor
public class FilmController {
    private final FilmDbService filmService;
    private final LikeDbService likeDbService;

    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) {
        return filmService.addFilm(film);
    }

    @PutMapping("/films")
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    @GetMapping("/films/{id}")
    public Film getFilmById(@PathVariable int id) {
        log.info("Get film by id " + id);

        return filmService.getFilmById(id);
    }

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        log.info("get all films controller");

        return filmService.getFilmsList(10);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void likeFilm(@PathVariable int id, @PathVariable int userId) {
        log.info("like film " + id + " by " + userId);

        likeDbService.like(id, userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void unlikeFilm(@PathVariable int id, @PathVariable int userId) {
        log.info("unlike film " + id + " by " + userId);

        likeDbService.unlike(id, userId);
    }
    
    @GetMapping("/films/popular")
    public List<Film> getBestFilms(@RequestParam(required = false) Integer count) {
        log.info("get best films.");

        return likeDbService.getMostPopularFilms(count);
    }

}
