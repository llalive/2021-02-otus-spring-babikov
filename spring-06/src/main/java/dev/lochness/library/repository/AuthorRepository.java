package dev.lochness.library.repository;

import dev.lochness.library.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    Long getAuthorsCount();

    Author saveAuthor(Author author);

    void deleteAuthorById(long id);

    List<Author> findAll();

    Optional<Author> findAuthorById(long id);
}
