package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class User {
    @NotNull
    private Integer id;
    @Email
    private String email;
    @NotBlank
    private String login;
    @NotBlank
    private String name;
    @Past
    @NotNull
    private LocalDate birthdate;


}
