package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class Rating extends AbstractEntity {
    @Size(max = 10)
    private String name;

    public Rating(int id, String name) {
        super(id);
        this.name = name;
    }

    public Rating(int id) {
        super(id);
        this.name = "";
    }

    public Rating() {
        this.name = "";
    }
}