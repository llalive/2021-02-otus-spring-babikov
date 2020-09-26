package dev.lochness.library.service;

import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Book;
import dev.lochness.library.domain.Comment;
import dev.lochness.library.domain.Genre;

import java.util.List;

public interface LibraryService {
    void listBooks();

    void listAuthors();

    void listGenres();

    void printBookInfo(String id);

    Book addBook(Book book);

    void deleteBook(String id);

    void setBookGenres(String bookId, List<String> genres);

    void setBookAuthors(String bookId, List<String> authors);

    void printBooksByGenre(String genreId);

    void printBooksByAuthor(String authorId);

    Genre addGenre(Genre genre);

    Author addAuthor(Author author);

    void deleteGenre(String genreId);

    void deleteAuthor(String authorId);

    void updateBook(String bookId, String title, String isbn);

    void addComment(String bookId, Comment comment);
}
