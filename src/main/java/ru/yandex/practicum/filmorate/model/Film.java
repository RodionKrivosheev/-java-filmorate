package ru.yandex.practicum.filmorate.model;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
public class Film {
    private int id;
    @NotNull
    private final String name;
    @NotNull
    @NotBlank
    private final String description;
    @NotNull
    private final LocalDate dateReliese;
    @NotNull
    @Positive
    private final int moveTime;
}



