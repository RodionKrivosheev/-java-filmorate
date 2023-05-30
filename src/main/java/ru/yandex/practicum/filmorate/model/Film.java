package ru.yandex.practicum.filmorate.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
@Data
@AllArgsConstructor
public class Film {
    @NotNull
    private Integer id;
    @NotBlank
    private final String name;
    @Max(200)
    @NotBlank
    private String description;
    @NotNull
    private final LocalDate dateReliese;
    @NotNull
    @Positive
    private final int moveTime;

    private static final LocalDate minDate = LocalDate.of(1895, 12, 28);

    public static void validate(Film film) {
        if (film.getDateReliese().isBefore(minDate)) {
            throw new ValidationException("Дата релиза должна быть < 28 декабря 1895 года!");
        }
    }

}

