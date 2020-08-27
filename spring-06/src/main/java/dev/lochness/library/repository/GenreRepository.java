package dev.lochness.library.repository;

import dev.lochness.library.domain.Genre;

import java.util.List;

public interface GenreRepository {
    Long getGenresCount();

    Genre saveGenre(Genre genre);

    void deleteGenreById(Long id);

    Genre findGenreById(Long id);

    List<Genre> findAll();
}
