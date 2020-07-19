package dev.lochness.library.service;

import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Book;
import dev.lochness.library.domain.Genre;

import java.util.List;

public interface LibraryService {
    void listBooks();

    void listAuthors();

    void listGenres();

    void printBookInfo(long id);

    void addBook(Book book);

    void deleteBook(long id);

    void setBookGenres(long bookId, List<Integer> genresId);

    void setBookAuthors(long bookId, List<Long> authorsId);

    void printBooksByGenre(int genreId);

    void printBooksByAuthor(long authorId);

    void addGenre(Genre genre);

    void addAuthor(Author author);

    void deleteGenre(int genreId);

    void deleteAuthor(long authorId);

    void updateBook(long bookId, String title, String isbn);
}
