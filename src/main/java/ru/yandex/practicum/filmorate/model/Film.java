package ru.yandex.practicum.filmorate.model;


import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Film {
    @NotNull
    private int id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    private LocalDate dateRelease;
    @NotNull
    @Positive
    private int duration;
}




