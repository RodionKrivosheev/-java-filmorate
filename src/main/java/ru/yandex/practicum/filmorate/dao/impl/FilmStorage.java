package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.auxilary.DaoHelper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FilmStorage implements ru.yandex.practicum.filmorate.dao.FilmStorageIn {
    final JdbcTemplate jdbcTemplate;

    private final DaoHelper daoHelper;

    @Override
    public Film addFilm(Film film) {
        String sql =
                "INSERT INTO films " +
                        "(film_title, film_description, film_release_date, film_duration, film_rating_id) " +
                        "VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        java.sql.Date sqlDate = Date.valueOf(film.getReleaseDate());

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"film_id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, sqlDate);
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);

        int id = (int) Objects.requireNonNull(keyHolder.getKey()).longValue();

        List<Genre> filmGenres = addGenresToFilm(id, film.getGenres());
        film.setGenres(filmGenres);

        return getFilmById(id);
    }

    @Override
    public Film getFilmById(int filmId) {
        String sql =
                "SELECT * FROM films WHERE film_id = ?";

        return jdbcTemplate.queryForObject(sql, daoHelper::makeFilm, filmId);
    }

    @Override
    public Film updateFilm(Film film) {
        String sql =
                "UPDATE films SET " +
                        "film_title = ?, film_description = ?, " +
                        "film_release_date = ?, film_duration = ?, film_rating_id = ? " +
                        "WHERE film_id = ?";

        jdbcTemplate.update(sql,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());

        deleteGenresForFilm(film.getId());
        List<Genre> filmGenres = addGenresToFilm(film.getId(), film.getGenres());
        film.setGenres(filmGenres);

        return getFilmById(film.getId());
    }

    @Override
    public List<Film> getFilmsList(int max) {
        String sql =
                "SELECT * FROM films LIMIT ?";

        return jdbcTemplate.query(sql, daoHelper::makeFilm, max);
    }

    private List<Genre> addGenresToFilm(int filmId, List<Genre> genres) {
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
        // таким образом нужно это сделать?
        /*return  jdbcTemplate.batchUpdate("MERGE INTO film_genre " +
                "(film_id, genre_id) " +
                "KEY(film_id, genre_id) " +
                "VALUES (?, ?)", SqlParameterSourceUtils.createBatch(genres));
                */
        return daoHelper.getGenresListForFilm(filmId);
    }

    private void deleteGenresForFilm(int filmId) {
        String sql =
                "DELETE FROM film_genre " +
                        "WHERE film_id = ?";

        jdbcTemplate.update(sql, filmId);
    }
}
