package dev.lochness.library.service;

import dev.lochness.library.dao.AuthorDao;
import dev.lochness.library.dao.BookDao;
import dev.lochness.library.dao.GenreDao;
import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Book;
import dev.lochness.library.domain.Genre;
import dev.lochness.library.io.Printer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DisplayName("Class LibraryServiceImpl")
class LibraryServiceImplTest {

    private static final String TEST_BOOK_TITLE = "Test book";
    private static final String TEST_BOOK_ISBN = "0123456789";
    private static final String TEST_AUTHOR_FIRST_NAME = "Sam";
    private static final String TEST_AUTHOR_LAST_NAME = "Sepiol";

    @MockBean
    private Printer printer;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private GenreDao genreDao;
    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private LibraryService service;

    @BeforeEach
    void setUp() {

    }

    @Test
    void shouldPrintOneBook() {
        service.listBooks();
        verify(printer, times(1)).printBookInfo(any(Book.class));
    }

    @Test
    void shouldPrintBookInfo() {
        service.printBookInfo(1);
        verify(printer, times(1)).printBookInfo(any(Book.class));
    }

    @Test
    void shouldPrintOneAuthor() {
        service.listAuthors();
        verify(printer, times(1)).printAuthor(any(Author.class));
    }

    @Test
    void shouldPrintAllGenres() {
        service.listGenres();
        verify(printer, times(8)).printGenre(any(Genre.class));
    }

    @Test
    @DirtiesContext
    void shouldAddBooksCorrectly() {
        Book book = new Book(TEST_BOOK_TITLE, TEST_BOOK_ISBN);
        service.addBook(book);
        Book addedBook = bookDao.getById(2);
        assertAll("Title and isbn are same",
                () -> assertEquals(TEST_BOOK_TITLE, addedBook.getTitle()),
                () -> assertEquals(TEST_BOOK_ISBN, addedBook.getISBN()));
    }

    @Test
    @DirtiesContext
    void shouldCorrectlyDeleteBookFromLibrary() {
        service.deleteBook(1);
        assertEquals(0, bookDao.count());
    }

    @Test
    @DirtiesContext
    void shouldSetBookGenresCorrectly() {
        service.setBookGenres(1, List.of(1, 3));
        List<Genre> genres = genreDao.getGenresForBook(1);
        assertAll("Should contain correct genres",
                () -> assertEquals(1, genres.get(0).getId()),
                () -> assertEquals(3, genres.get(1).getId()));
    }

    @Test
    @DirtiesContext
    void shouldSetBookAuthorsCorrectly() {
        service.addAuthor(new Author(TEST_AUTHOR_FIRST_NAME, TEST_AUTHOR_LAST_NAME));
        service.setBookAuthors(1, List.of(2L));
        assertEquals(2L, authorDao.getAuthorsForBook(1).get(0).getId());
    }

    @Test
    @DirtiesContext
    void shouldCorrectlyUpdateBookInfo() {
        service.updateBook(1, TEST_BOOK_TITLE, TEST_BOOK_ISBN);
        Book book = bookDao.getById(1);
        assertAll("Title and isbn must be changed",
                () -> assertEquals(TEST_BOOK_TITLE, book.getTitle()),
                () -> assertEquals(TEST_BOOK_ISBN, book.getISBN()));

    }
}