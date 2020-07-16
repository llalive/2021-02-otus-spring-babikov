package dev.lochness.library.dao;

import dev.lochness.library.domain.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("Class BookDaoJdbc")
class BookDaoJdbcTest {

    private static final String TEST_BOOK_TITLE = "Test book";
    private static final String TEST_BOOK_ISBN = "0123456789";
    private static final String DEFAULT_BOOK_TITLE = "Молчание ягнят";
    private static final int DEFAULT_BOOK_GENRE = 6;
    private static final long DEFAULT_BOOK_AUTHOR = 1;
    private static final String BOOK_SEARCH_QUERY = "ягнят";

    @Autowired
    private BookDao dao;

    @Test
    void shouldReturnCorrectNumberOfBooks() {
        assertEquals(1, dao.count());
    }

    @Test
    @DirtiesContext
    void addedBookShouldBeSetCorrectly() {
        dao.add(new Book(TEST_BOOK_TITLE, TEST_BOOK_ISBN));
        Book book = dao.getById(2);
        assertAll("Should add book correctly",
                () -> assertEquals(TEST_BOOK_TITLE, book.getTitle()),
                () -> assertEquals(TEST_BOOK_ISBN, book.getISBN())
        );
    }

    @Test
    @DirtiesContext
    void shouldUpdateBookCorrectly() {
        dao.update(new Book(1, TEST_BOOK_TITLE, TEST_BOOK_ISBN));
        Book book = dao.getById(1);
        assertAll("Should update book correctly",
                () -> assertEquals(TEST_BOOK_TITLE, book.getTitle()),
                () -> assertEquals(TEST_BOOK_ISBN, book.getISBN())
        );
    }

    @Test
    @DirtiesContext
    void shouldNotContainRemovedBook() {
        dao.deleteById(1);
        assertEquals(0, dao.count());
    }

    @Test
    void shouldReturnCorrectBookById() {
        Book book = dao.getById(1);
        assertEquals(DEFAULT_BOOK_TITLE, book.getTitle());
    }

    @Test
    @DirtiesContext
    void shouldReturnAllBooksInLibrary() {
        dao.add(new Book(TEST_BOOK_TITLE, TEST_BOOK_ISBN));
        assertEquals(2, dao.getAll().size());
    }

    @Test
    void shouldReturnCorrectListOfBooksByAuthor() {
        List<Book> books = dao.getByAuthorId(DEFAULT_BOOK_AUTHOR);
        assertEquals(1, books.get(0).getId());
    }

    @Test
    void shouldReturnCorrectListOfBooksByGenre() {
        List<Book> books = dao.getByGenre(DEFAULT_BOOK_GENRE);
        assertEquals(1, books.get(0).getId());
    }

    @Test
    void shouldReturnCorrectListOfBooksBySearchQuery() {
        List<Book> books = dao.search(BOOK_SEARCH_QUERY);
        assertEquals(1, books.size());
    }
}