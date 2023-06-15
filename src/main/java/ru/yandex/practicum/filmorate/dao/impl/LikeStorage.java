package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Component
public class LikeStorage implements ru.yandex.practicum.filmorate.dao.LikeStorage {
    private final JdbcTemplate jdbcTemplate;
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
        Film film = new Film(1, "God Father", null, "Film about father",
                LocalDate.now(), 240, null);
        film.setId(rs.getInt("film_id"));
        film.setName(rs.getString("film_title"));
        film.setDescription(rs.getString("film_description"));
        film.setReleaseDate(rs.getDate("film_release_date").toLocalDate());
        film.setDuration(rs.getInt("film_duration"));
        film.setMpa(ratingStorage.getRatingById(rs.getInt("FILM_RATING_ID")));
        return film;
    }
}
