package dev.lochness.library.repository;

import dev.lochness.library.domain.Book;

import java.util.List;

public interface BookRepository {
    Long getBooksCount();

    Book saveBook(Book book);

    void deleteBookById(long id);

    Book findBookById(long id);

    List<Book> findAll();
}
