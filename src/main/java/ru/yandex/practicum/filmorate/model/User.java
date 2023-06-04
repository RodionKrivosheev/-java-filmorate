package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;
import lombok.AllArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class User {
    @NotNull
    private int id;
    @NonNull
    @Email
    @NotBlank
    private String email;
    @NonNull
    @NotBlank
    private String login;

    private String name;
    @NonNull
    @Past
    @NotNull
    private LocalDate birthday;
}

