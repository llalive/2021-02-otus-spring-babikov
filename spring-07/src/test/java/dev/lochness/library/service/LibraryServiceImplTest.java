package dev.lochness.library.service;

import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Book;
import dev.lochness.library.domain.Comment;
import dev.lochness.library.domain.Genre;
import dev.lochness.library.io.Printer;
import dev.lochness.library.repository.BookRepository;
import dev.lochness.library.repository.GenreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    private GenreRepository genreRepository;

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
        Optional<Book> addedBook = bookRepository.findById(2L);
        assertThat(addedBook).isPresent().isEqualTo(book);
    }

    @Test
    @DirtiesContext
    void shouldCorrectlyDeleteBookFromLibrary() {
        service.deleteBook(1);
        assertEquals(0, bookRepository.count());
    }

    @Test
    @DirtiesContext
    @Transactional
    void shouldSetBookGenresCorrectly() {
        var id = List.of(1L, 3L);
        service.setBookGenres(1, id);
        assertThat(bookRepository.findById(1L).get().getGenres())
                .containsAll(genreRepository.findAllById(id));
    }

    @Test
    @DirtiesContext
    void shouldSetBookAuthorsCorrectly() {
        var author =
                service.addAuthor(Author.builder()
                        .firstName(TEST_AUTHOR_FIRST_NAME)
                        .lastName(TEST_AUTHOR_LAST_NAME)
                        .build());
        service.setBookAuthors(1, List.of(author.getId()));
        assertThat(bookRepository.findById(1L).get().getAuthors())
                .hasSize(1)
                .contains(author);
    }

    @Test
    @DirtiesContext
    void shouldCorrectlyUpdateBookInfo() {
        service.updateBook(1L, TEST_BOOK_TITLE, TEST_BOOK_ISBN);
        var book = bookRepository.findById(1L).get();
        assertAll("Title and isbn must be changed",
                () -> assertEquals(TEST_BOOK_TITLE, book.getTitle()),
                () -> assertEquals(TEST_BOOK_ISBN, book.getIsbn()));
    }

    @Test
    @DirtiesContext
    @Transactional
    void shouldCorrectlyAddCommentsForBook() {
        var comment = new Comment(0L, COMMENT_AUTHOR, COMMENT_TEXT);
        service.addComment(1L, comment);
        var book = bookRepository.findById(1L);
        assertAll(() -> assertFalse(book.get().getComments().isEmpty()),
                () -> assertEquals(book.get().getComments().get(0).getCommentedBy(), COMMENT_AUTHOR),
                () -> assertEquals(book.get().getComments().get(0).getText(), COMMENT_TEXT));

    }
}