package ru.yandex.practicum.filmorate.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.auxilary.DaoHelper;
import ru.yandex.practicum.filmorate.dao.LikeStorageIn;
import ru.yandex.practicum.filmorate.model.Film;


import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class LikeStorage implements LikeStorageIn {
    private final JdbcTemplate jdbcTemplate;

    private final DaoHelper daoHelper;

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

        return jdbcTemplate.query(sql, daoHelper::makeFilm, filmsCount);
    }
}
