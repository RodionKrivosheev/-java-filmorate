package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.AbstractEntity;

import java.util.List;

//Чтобы не повторяться

public interface CommonStorage <E extends AbstractEntity> {
    E findById(int id);

    List<E> findAll();

    E add(E data);

    E update(E data);
}