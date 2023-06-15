package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.FilmRating;
import ru.yandex.practicum.filmorate.service.RatingDbService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Component
@RequestMapping("/mpa")
public class RatingController {
    private final RatingDbService ratingDbService;

    @GetMapping
    public List<FilmRating> getRatingList() {
        return ratingDbService.getRatingList();
    }

    @GetMapping("/{id}")
    public FilmRating getRatingById(@PathVariable int id) {
        return ratingDbService.getRatingById(id);
    }
}
