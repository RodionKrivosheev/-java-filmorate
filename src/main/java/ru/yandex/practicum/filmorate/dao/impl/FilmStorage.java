package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.FilmDbService;
import ru.yandex.practicum.filmorate.service.GenreDbService;
import ru.yandex.practicum.filmorate.service.RatingDbService;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FilmStorage implements ru.yandex.practicum.filmorate.dao.FilmStorage {
    final JdbcTemplate jdbcTemplate;

    private final RatingStorage ratingStorage;

    private final GenreStorage genreStorage;

    @Override
    public Film addFilm(Film film) {
        String sql =
                        "INSERT INTO films " +
                        "(film_title, film_description, film_release_date, film_duration, film_rating_id) " +
                        "VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        Date sqlDate = Date.valueOf(film.getReleaseDate());

        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sql, new String[]{"film_id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, sqlDate);
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);

        int id = (int) keyHolder.getKey().longValue();

        List<Genre> genres = genreStorage.addGenresToFilm(id, film.getGenres());
        film.setGenres(genres);

        return getFilmById(id);
    }

    @Override
    public Film getFilmById(int filmId) {
        String sql =
                "SELECT * FROM films WHERE film_id = ?";

        return jdbcTemplate.queryForObject(sql, this::makeFilm, filmId);
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

        genreStorage.deleteGenresForFilm(film.getId());
        List<Genre> genres = genreStorage.addGenresToFilm(film.getId(), film.getGenres());
        film.setGenres(genres);

        return getFilmById(film.getId());
    }

    @Override
    public List<Film> getFilmsList(int max) {
        String sql =
                "SELECT * FROM films LIMIT ?";

        return jdbcTemplate.query(sql, this::makeFilm, max);
    }

    private Genre mapToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        Genre genre = new Genre(1,"11");
        genre.setId(resultSet.getInt("GENRE_ID"));
        genre.setName(resultSet.getString("NAME"));
        return genre;
    }

    /*private Film makeFilm(ResultSet resultSet, int rowNum) throws SQLException {
        Film film = new Film(1, "Film1", "" "" "Description1", LocalDate.of(1994, 11,
                2), 190);
        film.setId(resultSet.getInt("FILM_ID"));
        film.setName(resultSet.getString("NAME"));
        film.setDescription(resultSet.getString("DESCRIPTION"));
        film.setReleaseDate(resultSet.getDate("RELEASE_DATE").toLocalDate());
        film.setDuration(resultSet.getInt("DURATION"));
        film.setMpa(new FilmRating(resultSet.getInt("RATING_ID"), resultSet.getString("R_NAME")));
        return film;
    }

    /* private Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
         Film film = new Film(1, "God Father", null, "Film about father",
                 LocalDate.now(), 240, null);
         film.setId(rs.getInt("film_id"));
         film.setName(rs.getString("film_title"));
         film.setDescription(rs.getString("film_description"));
         film.setReleaseDate(rs.getDate("film_release_date").toLocalDate());
         film.setDuration(rs.getInt("film_duration"));
         film.setMpa();
         return film;
     }*/
    private Film makeFilm(ResultSet rs, int rowNum) throws SQLException {
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
}
