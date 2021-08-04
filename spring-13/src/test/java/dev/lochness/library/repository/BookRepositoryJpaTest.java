package dev.lochness.library.repository;

import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Book;
import dev.lochness.library.domain.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Class BookRepositoryJpa")
@DataJpaTest
class BookRepositoryJpaTest {

    private static final String TEST_BOOK_TITLE = "Test book";
    private static final String TEST_BOOK_ISBN = "0123456789";
    private static final String TEST_AUTHOR_FIRST_NAME = "Петр Павлович";
    private static final String TEST_AUTHOR_LAST_NAME = "Чехов";
    private static final String TEST_GENRE_NAME = "фэнтези";
    private static final String DEFAULT_BOOK_TITLE = "Молчание ягнят";
    private static final long DEFAULT_BOOK_ID = 1L;
    private static final long EXPECTED_ADDED_BOOK_ID = 4L;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DirtiesContext
    void addedBookShouldBeSetCorrectly() {
        Book addedBook = bookRepository.save(Book.builder()
                .title(TEST_BOOK_TITLE)
                .isbn(TEST_BOOK_ISBN)
                .build());
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
        authors.add(Author.builder()
                .firstName(TEST_AUTHOR_FIRST_NAME)
                .lastName(TEST_AUTHOR_LAST_NAME)
                .build());
        expected.setAuthors(authors);
        expected.getGenres().add(Genre.builder().name(TEST_GENRE_NAME).build());
        bookRepository.save(expected);
        Optional<Book> actual = bookRepository.findById(DEFAULT_BOOK_ID);
        assertThat(actual).isPresent().get().isEqualToComparingFieldByField(expected);
    }

    @Test
    void shouldReturnCorrectBookById() {
        assertThat(bookRepository.findById(1L)).isPresent().get()
                .hasFieldOrPropertyWithValue("title", DEFAULT_BOOK_TITLE);
    }

    @Test
    @DirtiesContext
    void shouldReturnAllBooksInLibrary() {
        bookRepository.save(Book.builder()
                .title(TEST_BOOK_TITLE)
                .isbn(TEST_BOOK_ISBN)
                .build());
        List<Book> books = bookRepository.findAll();
        assertEquals(4, books.size());
    }
}