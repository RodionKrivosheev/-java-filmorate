package ru.yandex.practicum.filmorate.model;


import lombok.*;
import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
public class Film {
    private int id;

    private final String name;
    @NotBlank
    private final String description;

    private final LocalDate dateReliese;

    @Positive
    private final int moveTime;
}




