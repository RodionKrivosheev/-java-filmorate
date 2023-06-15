package ru.yandex.practicum.filmorate.auxilary;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.impl.GenreStorage;
import ru.yandex.practicum.filmorate.dao.impl.RatingStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class DaoHelper {
    private final RatingStorage ratingStorage;
    private final GenreStorage genreStorage;

    public Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        return Film.builder()
                .id(rs.getInt("film_id"))
                .name(rs.getString("film_title"))
                .genres(genreStorage.getGenresListForFilm(rs.getInt("film_id")))
                .description(rs.getString("film_description"))
                .releaseDate(rs.getDate("film_release_date").toLocalDate())
                .duration(rs.getInt("film_duration"))
                .mpa(ratingStorage.getRatingById(rs.getInt("film_rating_id")))
                .build();
    }

    public User makeUser(ResultSet rs, int rowNum) throws SQLException {
        return User.builder()
                .id(rs.getInt("user_id"))
                .email(rs.getString("user_email"))
                .login(rs.getString("user_login"))
                .name(rs.getString("user_name"))
                .birthday(rs.getDate("user_birthday").toLocalDate())
                .build();
    }

    public List<Genre> getGenresListForFilm(int filmId) {
        return genreStorage.getGenresListForFilm(filmId);
    }

}
