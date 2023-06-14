package ru.yandex.practicum.filmorate.service.Dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreDbService;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GenreStorageTest {
    private final GenreDbService genreDbService;

    @Test
    public void getGenreList() {
        List<Genre> genreList = genreDbService.getGenresList();

        checkGenre(genreList.get(0));
        checkGenre(genreList.get(1));
        checkGenre(genreList.get(2));
        checkGenre(genreList.get(3));
        checkGenre(genreList.get(4));
        checkGenre(genreList.get(5));
    }

    @Test
    public void getGenreById() {
        checkGenre(genreDbService.getGenreById(1));
    }

    private void checkGenre(Genre genre) {
        int id = genre.getId();
        String name = genre.getName();
        String expectedName;

        switch (id) {
            case 1:
                expectedName = "Комедия";
                break;
            case 2:
                expectedName = "Драма";
                break;
            case 3:
                expectedName = "Мультфильм";
                break;
            case 4:
                expectedName = "Триллер";
                break;
            case 5:
                expectedName = "Документальный";
                break;
            case 6:
                expectedName = "Боевик";
                break;
            default:
                expectedName = "";
        }

        assertThat(name, is(expectedName));
    }
}
