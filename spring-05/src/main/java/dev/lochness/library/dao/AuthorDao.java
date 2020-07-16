package dev.lochness.library.dao;

import dev.lochness.library.domain.Author;

import java.util.List;

public interface AuthorDao {
    int count();

    void add(Author author);

    void deleteById(long id);

    List<Author> getAll();

    Author getById(long id);

    List<Author> getAuthorsForBook(long bookId);
}
