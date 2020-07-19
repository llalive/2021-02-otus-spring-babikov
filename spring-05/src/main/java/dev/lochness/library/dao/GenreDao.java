package dev.lochness.library.dao;

import dev.lochness.library.domain.Genre;

import java.util.List;

public interface GenreDao {
    int count();

    void add(Genre genre);

    void deleteById(Integer id);

    Genre getById(Integer id);

    List<Genre> getAll();

    List<Genre> getGenresForBook(long bookId);
}
