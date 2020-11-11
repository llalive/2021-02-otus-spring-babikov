package dev.lochness.library.service;

import dev.lochness.library.domain.Book;
import dev.lochness.library.dto.BookBriefDto;
import dev.lochness.library.repository.AuthorRepository;
import dev.lochness.library.repository.BookRepository;
import dev.lochness.library.repository.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("Class LibraryServiceImpl")
class LibraryServiceImplTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private LibraryService service;

    @Value("${books-per-page-count}")
    private int booksPerPage;

    @Test
    void shouldReturnCorrectNumberOfBooks() {
        List<BookBriefDto> booksWithOffset = service.getBooksWithOffset(1);
        Assertions.assertEquals(booksPerPage, booksWithOffset.size());
    }

    @Test
    void shouldNotReturnAnyBooksForIncorrectPage() {
        List<BookBriefDto> booksWithOffset = service.getBooksWithOffset(5);
        Assertions.assertEquals(0, booksWithOffset.size());
    }

    @Test
    @DirtiesContext
    void shouldAddBookCorrectly() {
        long booksCount = bookRepository.count();
        Book book = Book.builder()
                .title("Title")
                .genres(new ArrayList<>())
                .authors(new ArrayList<>())
                .build();
        service.updateBook(book);
        assertEquals(booksCount + 1, bookRepository.count());
    }

    @Test
    @DirtiesContext
    void shouldRemoveBookCorrectly() {
        Book book = bookRepository.findAll().get(0);
        service.deleteBook(book.getId());
        Optional<Book> expected = bookRepository.findById(book.getId());
        Assertions.assertTrue(expected.isEmpty());
    }

    @Test
    void shouldReturnAllGenres() {
        Assertions.assertArrayEquals(genreRepository.findAll().toArray(), service.getGenres().toArray());
    }

    @Test
    void shouldReturnAllAuthors() {
        Assertions.assertArrayEquals(authorRepository.findAll().toArray(), service.getAuthors().toArray());
    }
}