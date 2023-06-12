package ru.yandex.practicum.filmorate.storage.db_impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.db.GenreDbStorage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class GenreDbStorageTest {
    private final GenreDbStorage genreStorage;
    private int countRec;   //Так как в таблице уже есть начальные данные

    @BeforeEach
    void setUp() {
        countRec = genreStorage.findAll().size();
    }

    @Test
    void findById() {
        int id1 = countRec++;
        Genre expGenre = getExpGenre1(id1);
        genreStorage.add(expGenre);
        Genre actGenre = genreStorage.findById(expGenre.getId());
        assertEquals(expGenre.getId(), actGenre.getId());
        assertEquals(expGenre.getName(), actGenre.getName());
    }

    @Test
    void findAll() {
        int id1 = countRec++;
        Genre expGenre1 = getExpGenre1(id1);
        genreStorage.add(expGenre1);
        int id2 = countRec++;
        Genre expGenre2 = getExpGenre2(id2);
        genreStorage.add(expGenre2);

        List<Genre> actGenres = genreStorage.findAll();
        int i1 = actGenres.size() - 2;
        int i2 = actGenres.size() - 1;
        assertEquals(expGenre1,  actGenres.get(i1));
        assertEquals(expGenre2, actGenres.get(i2));
        assertEquals(countRec, actGenres.size());
    }

    @Test
    void create() {
        int id1 = countRec++;
        Genre expGenre = getExpGenre1((id1));
        genreStorage.add(expGenre);
        Genre actGenre = genreStorage.findById(expGenre.getId());
        assertEquals(expGenre.getId(),actGenre.getId());
        assertEquals(expGenre.getName(),actGenre.getName());
    }

    @Test
    void update() {
        int id1 = countRec++;
        Genre expGenre = getExpGenre1(id1);
        genreStorage.add(expGenre);
        expGenre.setName("action");

        genreStorage.update(expGenre);
        Genre actGenre = genreStorage.findById(expGenre.getId());

        assertEquals(expGenre.getId(), actGenre.getId());
        assertEquals(expGenre.getName(), actGenre.getName());
    }

    private Genre getExpGenre1(int id) {
        Genre genre = new Genre();
        genre.setId(id);
        genre.setName("Genre1");
        return genre;
    }

    private Genre getExpGenre2(int id) {
        Genre genre = new Genre();
        genre.setId(id);
        genre.setName("Genre2");
        return genre;
    }
}