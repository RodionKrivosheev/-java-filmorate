package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GenreStorage implements ru.yandex.practicum.filmorate.dao.GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getGenresList() {
        String sql =
                "SELECT * FROM genres";

        return jdbcTemplate.query(sql, this::makeGenre);
    }

    @Override
    public List<Genre> getGenresListForFilm(int filmId) {
        String sql =
                "SELECT fg.*, g.genre_title FROM film_genre AS fg " +
                        "JOIN genres AS g ON g.genre_id = fg.genre_id " +
                        "WHERE fg.film_id = ?";

        return jdbcTemplate.query(sql, this::makeGenre, filmId);
    }

    @Override
    public void deleteGenresForFilm(int filmId) {
        String sql =
                "DELETE FROM film_genre " +
                        "WHERE film_id = ?";

        jdbcTemplate.update(sql, filmId);
    }

    @Override
    public List<Genre> addGenresToFilm(int filmId, List<Genre> genres) {
        String sql =
                "MERGE INTO film_genre " +
                        "(film_id, genre_id) " +
                        "KEY(film_id, genre_id) " +
                        "VALUES (?, ?)";

        if (genres == null || genres.isEmpty()) {
            return new ArrayList<>();
        }

        for (Genre genre : genres) {
            jdbcTemplate.update(sql, filmId, genre.getId());
        }
        return getGenresListForFilm(filmId);
    }

    @Override
    public Genre getGenreById(int genreId) {
        String sql =
                "SELECT * FROM genres WHERE genre_id = ?";

        return jdbcTemplate.queryForObject(sql, this::makeGenre, genreId);
    }

    private Genre makeGenre(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(rs.getInt("genre_id"),
                rs.getString("genre_title"));
    }
}
