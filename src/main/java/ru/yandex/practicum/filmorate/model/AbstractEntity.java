package ru.yandex.practicum.filmorate.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public abstract class AbstractEntity {
    private int id;
    public AbstractEntity(int id) {
        this.id = id;
    }

    public AbstractEntity() {
    }
}
