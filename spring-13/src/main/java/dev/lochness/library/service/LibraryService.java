package dev.lochness.library.service;

import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Book;
import dev.lochness.library.domain.Genre;
import dev.lochness.library.dto.BookBriefDto;
import dev.lochness.library.dto.BookDetailsDto;

import java.util.List;
import java.util.Optional;

public interface LibraryService {

    List<BookBriefDto> getBooks();

    Optional<BookDetailsDto> getBookById(Long id);

    Book updateOrCreateBook(Book book);

    void deleteBook(Long id);

    List<Genre> getGenres();

    List<Author> getAuthors();
}
