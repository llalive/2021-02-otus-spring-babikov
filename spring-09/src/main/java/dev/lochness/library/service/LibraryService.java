package dev.lochness.library.service;

import dev.lochness.library.domain.Book;
import dev.lochness.library.dto.BookBriefDto;
import dev.lochness.library.dto.BookDetailsDto;

import java.util.List;
import java.util.Optional;

public interface LibraryService {

    List<BookBriefDto> getBooksWithOffset(int offset);

    Optional<BookDetailsDto> getBookById(Long id);

    Book updateBook(Book book);
/*
    void listBooks();

    void listAuthors();

    void listGenres();

    void printBookInfo(long id);

    Book addBook(Book book);

    void deleteBook(long id);

    void setBookGenres(long bookId, List<Long> genres);

    void setBookAuthors(long bookId, List<Long> authors);

    void printBooksByGenre(long genreId);

    void printBooksByAuthor(long authorId);

    Genre addGenre(Genre genre);

    Author addAuthor(Author author);

    void deleteGenre(long genreId);

    void deleteAuthor(long authorId);

    void updateBook(long bookId, String title, String isbn);

    void addComment(long bookId, Comment comment);*/
}
