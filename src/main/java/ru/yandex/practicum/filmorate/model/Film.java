package ru.yandex.practicum.filmorate.model;


import lombok.AllArgsConstructor;
import lombok.Data;

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
    @NotBlank
    private String description;
    @NotNull
    private final LocalDate dateReliese;
    @NotNull
    @Positive
    private final int moveTime;


}


