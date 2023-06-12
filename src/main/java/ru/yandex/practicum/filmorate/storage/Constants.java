package ru.yandex.practicum.filmorate.storage;

import java.time.LocalDate;

public class Constants {
    public static final LocalDate MIN_DATE = LocalDate.of(1895, 12, 28);
    public static final String MSG_ERR_DATE = "Дата релиза не раньше 28 декабря 1895 года ";
    public static final String MSG_ERR_MPA = "Не заполнен рейтинг MPA";
    public static final String MSG_ERR_ID = "Некорректный id ";
    public static final String MSG_ERR_NOT_FOUND = "Не найдено по id ";
}
