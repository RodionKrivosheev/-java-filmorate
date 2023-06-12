package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.AbstractEntity;

import java.util.List;

public interface CommonService <E extends AbstractEntity>{
    List<E> findAll();

    E add(E data);

    E update(E data);

    void validationBeforeCreate(E data);

    void validationBeforeUpdate(E data);

    void validateId(int id);

    E findById(int id);
}