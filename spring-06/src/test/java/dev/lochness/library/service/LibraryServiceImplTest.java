package dev.lochness.library.service;

import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Book;
import dev.lochness.library.domain.Comment;
import dev.lochness.library.domain.Genre;
import dev.lochness.library.io.Printer;
import dev.lochness.library.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DisplayName("Class LibraryServiceImpl")
class LibraryServiceImplTest {

    private static final String TEST_BOOK_TITLE = "Test book";
    private static final String TEST_BOOK_ISBN = "0123456789";
    private static final String TEST_AUTHOR_FIRST_NAME = "Sam";
    private static final String TEST_AUTHOR_LAST_NAME = "Sepiol";
    private static final String COMMENT_AUTHOR = "Charlz Dikkens";
    private static final String COMMENT_TEXT = "I can do better";

    @MockBean
    private Printer printer;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LibraryService service;

    @Test
    void shouldPrintOneBook() {
        service.listBooks();
        verify(printer, times(1)).printBookInfo(any(Book.class), eq(false));
    }

    @Test
    void shouldPrintBookInfo() {
        service.printBookInfo(1);
        verify(printer, times(1)).printBookInfo(any(Book.class), eq(true));
    }

    @Test
    void shouldPrintOneAuthor() {
        service.listAuthors();
        verify(printer, times(1)).printAuthor(any(Author.class), eq(false));
    }

    @Test
    void shouldPrintAllGenres() {
        service.listGenres();
        verify(printer, times(8)).printGenre(any(Genre.class), eq(false));
    }

    @Test
    @DirtiesContext
    void shouldAddBooksCorrectly() {
        Book book = Book.builder()
                .title(TEST_BOOK_TITLE)
                .isbn(TEST_BOOK_ISBN)
                .build();
        service.addBook(book);
        Book addedBook = bookRepository.findBookById(2);
        assertAll("Title and isbn are same",
                () -> assertEquals(TEST_BOOK_TITLE, addedBook.getTitle()),
                () -> assertEquals(TEST_BOOK_ISBN, addedBook.getIsbn()));
    }

    @Test
    @DirtiesContext
    void shouldCorrectlyDeleteBookFromLibrary() {
        service.deleteBook(1);
        assertEquals(0, bookRepository.getBooksCount());
    }

    @Test
    @DirtiesContext
    @Transactional
    void shouldSetBookGenresCorrectly() {
        service.setBookGenres(1, List.of(1L, 3L));
        Book book = bookRepository.findBookById(1);
        assertAll("Should contain correct genres",
                () -> assertEquals(1, book.getGenres().get(0).getId()),
                () -> assertEquals(3, book.getGenres().get(1).getId()));
    }

    @Test
    @DirtiesContext
    void shouldSetBookAuthorsCorrectly() {
        Author author =
                service.addAuthor(Author.builder()
                        .firstName(TEST_AUTHOR_FIRST_NAME)
                        .lastName(TEST_AUTHOR_LAST_NAME)
                        .build());
        service.setBookAuthors(1, List.of(author.getId()));
        Book book = bookRepository.findBookById(1L);
        assertEquals(2L, book.getAuthors().get(0).getId());
    }

    @Test
    @DirtiesContext
    void shouldCorrectlyUpdateBookInfo() {
        service.updateBook(1L, TEST_BOOK_TITLE, TEST_BOOK_ISBN);
        Book book = bookRepository.findBookById(1);
        assertAll("Title and isbn must be changed",
                () -> assertEquals(TEST_BOOK_TITLE, book.getTitle()),
                () -> assertEquals(TEST_BOOK_ISBN, book.getIsbn()));
    }

    @Test
    @DirtiesContext
    @Transactional
    void shouldCorrectlyAddCommentsForBook() {
        service.addComment(1L, new Comment(0L, COMMENT_AUTHOR, COMMENT_TEXT));
        Book book = bookRepository.findBookById(1);
        assertAll("Comments not empty and saved correctly",
                () -> assertFalse(book.getComments().isEmpty()),
                () -> assertEquals(book.getComments().get(0).getCommentedBy(), COMMENT_AUTHOR),
                () -> assertEquals(book.getComments().get(0).getText(), COMMENT_TEXT));
    }
}