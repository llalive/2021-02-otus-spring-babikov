package dev.lochness.library.repository;

import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Book;
import dev.lochness.library.domain.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Class BookRepositoryJpa")
@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {

    private static final String TEST_BOOK_TITLE = "Test book";
    private static final String TEST_BOOK_ISBN = "0123456789";
    private static final String TEST_AUTHOR_FIRST_NAME = "Петр Павлович";
    private static final String TEST_AUTHOR_LAST_NAME = "Чехов";
    private static final String TEST_GENRE_NAME = "фэнтези";
    private static final String DEFAULT_BOOK_TITLE = "Молчание ягнят";
    private static final int DEFAULT_BOOK_GENRE = 6;
    private static final long DEFAULT_BOOK_AUTHOR = 1;
    private static final long DEFAULT_BOOK_ID = 1L;
    private static final int EXPECTED_NUMBER_OF_BOOKS = 1;
    private static final long EXPECTED_ADDED_BOOK_ID = 2L;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void shouldReturnCorrectNumberOfBooks() {
        assertEquals(EXPECTED_NUMBER_OF_BOOKS, bookRepository.getBooksCount());
    }

    @Test
    @DirtiesContext
    void addedBookShouldBeSetCorrectly() {
        Book addedBook = bookRepository.saveBook(new Book(TEST_BOOK_TITLE, TEST_BOOK_ISBN));
        Book book = em.find(Book.class, EXPECTED_ADDED_BOOK_ID);
        assertThat(addedBook).isNotNull().isEqualToComparingFieldByField(book);
    }

    @Test
    @DirtiesContext
    void shouldUpdateBookCorrectly() {
        Book expected = em.find(Book.class, DEFAULT_BOOK_ID);
        expected.setTitle(TEST_BOOK_TITLE);
        expected.setIsbn(TEST_BOOK_ISBN);
        List<Author> authors = new ArrayList<>();
        authors.add(new Author(0L, TEST_AUTHOR_FIRST_NAME, TEST_AUTHOR_LAST_NAME, new ArrayList<>()));
        expected.setAuthors(authors);
        expected.getGenres().add(new Genre(0L, TEST_GENRE_NAME, new ArrayList<>()));
        bookRepository.saveBook(expected);
        Book actual = bookRepository.findBookById(DEFAULT_BOOK_ID);
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    @Test
    @DirtiesContext
    void shouldNotContainRemovedBook() {
        bookRepository.deleteBookById(1);
        assertEquals(0, bookRepository.getBooksCount());
    }

    @Test
    void shouldReturnCorrectBookById() {
        Book book = bookRepository.findBookById(1);
        assertEquals(DEFAULT_BOOK_TITLE, book.getTitle());
    }

    @Test
    @DirtiesContext
    void shouldReturnAllBooksInLibrary() {
        bookRepository.saveBook(new Book(TEST_BOOK_TITLE, TEST_BOOK_ISBN));
        List<Book> books = bookRepository.findAll();
        assertEquals(2, books.size());
    }

    @Test
    void shouldReturnCorrectListOfBooksByAuthor() {
        List<Book> books = bookRepository.findBooksByAuthorId(DEFAULT_BOOK_AUTHOR);
        assertEquals(1, books.get(0).getId());
    }

    @Test
    void shouldReturnCorrectListOfBooksByGenre() {
        List<Book> books = bookRepository.findBooksByGenreId(DEFAULT_BOOK_GENRE);
        assertEquals(1, books.get(0).getId());
    }
}