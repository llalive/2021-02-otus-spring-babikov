package dev.lochness.library.repository;

import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Genre;

import java.util.List;

public interface BookReporitoryCustom {
    void removeGenreArrayElementsById(String id);

    void removeAuthorArrayElementsById(String id);

    List<Author> getBookAuthorsById(String bookId);

    List<Genre> getBookGenresById(String bookId);
}
