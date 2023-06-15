package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.GenreStorage;
import ru.yandex.practicum.filmorate.dao.RatingStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.FilmRating;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class LikeStorage implements ru.yandex.practicum.filmorate.dao.LikeStorage {
    private final JdbcTemplate jdbcTemplate;
    private final GenreStorage genreStorage;
    private final RatingStorage ratingStorage;

    @Override
    public void like(int userId, int filmId) {
        String sql =
                "INSERT INTO users_liked_films (film_id, user_id) " +
                        "VALUES (?, ?)";

        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public void unlike(int userId, int filmId) {
        String sql =
                "DELETE FROM users_liked_films WHERE user_id = ? AND film_id = ?";

        jdbcTemplate.update(sql, userId, filmId);
    }

    @Override
    public List<Film> getMostPopularFilms(Integer filmsCount) {
        if (filmsCount == null) {
            filmsCount = 10;
        }
        String sql =
                "SELECT f.*, COUNT(l.user_id) " +
                        "FROM films AS f " +
                        "LEFT OUTER JOIN users_liked_films AS l " +
                        "ON l.film_id = f.film_id " +
                        "GROUP BY f.film_id " +
                        "ORDER BY COUNT(l.user_id) DESC " +
                        "LIMIT ?";

        return jdbcTemplate.query(sql, this::makeFilm, filmsCount);
    }

    private Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
        List<Genre> genreList = genreStorage.getGenresListForFilm(rs.getInt("film_id"));
        FilmRating rating = ratingStorage.getRatingById(rs.getInt("film_rating_id"));
        return Film.builder()
                .id(rs.getInt("film_id"))
                .name(rs.getString("film_title"))
                .genres(genreList)
                .description(rs.getString("film_description"))
                .releaseDate(rs.getDate("film_release_date").toLocalDate())
                .duration(rs.getInt("film_duration"))
                .mpa(rating)
                .build();
    }
}
