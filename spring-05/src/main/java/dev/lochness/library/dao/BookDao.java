package dev.lochness.library.dao;

import dev.lochness.library.domain.Book;

import java.util.List;

public interface BookDao {
    int count();

    void add(Book book);

    void update(Book book);

    void deleteById(long id);

    Book getById(long id);

    List<Book> getAll();

    List<Book> getByAuthorId(long authorId);

    List<Book> getByGenre(int genreId);

    List<Book> search(String title);
}
