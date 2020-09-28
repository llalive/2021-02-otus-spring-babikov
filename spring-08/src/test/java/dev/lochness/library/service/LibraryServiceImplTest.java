package dev.lochness.library.service;

import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Book;
import dev.lochness.library.domain.Comment;
import dev.lochness.library.domain.Genre;
import dev.lochness.library.io.Printer;
import dev.lochness.library.repository.AuthorRepository;
import dev.lochness.library.repository.BookRepository;
import dev.lochness.library.repository.CommentRepository;
import dev.lochness.library.repository.GenreRepository;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    private static final String COMMENT_AUTHOR = "Charles Dickens";
    private static final String COMMENT_TEXT = "I can do better";

    @MockBean
    private Printer printer;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private LibraryService service;

    private Book firstBook;

    @BeforeEach
    void setFirstBook() {
        firstBook = bookRepository.findAll().get(0);
    }

    @Test
    void shouldPrintTwoBooks() {
        service.listBooks();
        verify(printer, times(2)).printBookInfo(any(Book.class), eq(false));
    }

    @Test
    void shouldPrintBookInfo() {
        service.printBookInfo(bookRepository.findAll().get(0).getId());
        verify(printer, times(1)).printBookInfo(any(Book.class), eq(true));
    }

    @Test
    void shouldPrintAllAuthors() {
        service.listAuthors();
        verify(printer, times(2)).printAuthor(any(Author.class));
    }

    @Test
    void shouldPrintAllGenres() {
        service.listGenres();
        verify(printer, times(3)).printGenre(any(Genre.class));
    }

    @Test
    @DirtiesContext
    void shouldAddBooksCorrectly() {
        val book = Book.builder()
                .title(TEST_BOOK_TITLE)
                .isbn(TEST_BOOK_ISBN)
                .build();
        val addedBook = service.addBook(book);
        val searchBook = bookRepository.findById(addedBook.getId());
        assertTrue(searchBook.isPresent() && searchBook.get().equals(book));
    }

    @Test
    @DirtiesContext
    void shouldCorrectlyDeleteBookFromLibrary() {
        service.deleteBook(firstBook.getId());
        assertEquals(1, bookRepository.count());
    }

    @Test
    @DirtiesContext
    void shouldSetBookGenresCorrectly() {
        val genres = genreRepository.findAll();
        val book = bookRepository.findAll().get(0);
        val genresId = List.of(genres.get(0).getId(),
                genres.get(1).getId());
        service.setBookGenres(book.getId(), genresId);
        val updatedBook = bookRepository.findById(book.getId());
        assertTrue(updatedBook.isPresent() &&
                updatedBook.get().getGenres().containsAll(Arrays.asList(genres.get(0), genres.get(1))));
    }

    @Test
    @DirtiesContext
    void shouldSetBookAuthorsCorrectly() {
        val author = authorRepository.save(Author.builder()
                .firstName(TEST_AUTHOR_FIRST_NAME)
                .lastName(TEST_AUTHOR_LAST_NAME)
                .build());
        service.setBookAuthors(firstBook.getId(), List.of(author.getId()));
        val updatedBook = bookRepository.findById(firstBook.getId());
        assertTrue(updatedBook.isPresent()
                && updatedBook.get().getAuthors().get(0).equals(author));
    }

    @Test
    @DirtiesContext
    void shouldCorrectlyUpdateBookInfo() {
        service.updateBook(firstBook.getId(), TEST_BOOK_TITLE, TEST_BOOK_ISBN);
        val updatedBook = bookRepository.findBookByTitle(TEST_BOOK_TITLE);
        assertTrue(updatedBook.isPresent()
                && updatedBook.get().getTitle().equals(TEST_BOOK_TITLE)
                && updatedBook.get().getIsbn().equals(TEST_BOOK_ISBN));
    }

    @Test
    @DirtiesContext
    void shouldCorrectlyAddCommentsForBook() {
        var commentsCount = commentRepository.findAllByBookId(firstBook.getId()).size();
        var comment = Comment.builder()
                .text(COMMENT_TEXT).commentedBy(COMMENT_AUTHOR).build();
        service.addComment(firstBook.getId(), comment);
        val comments = commentRepository.findAllByBookId(firstBook.getId());
        assertThat(comments).hasSize(commentsCount + 1);
    }

    @Test
    @DirtiesContext
    void shouldClearCommentsForRemovedBook() {
        bookRepository.deleteById(firstBook.getId());
        assertThat(bookRepository.findById(firstBook.getId())).isNotPresent();
    }

    @Test
    @DirtiesContext
    void shouldCascadeRemoveAuthorFromBook() {
        val author = firstBook.getAuthors().get(0);
        authorRepository.deleteById(author.getId());
        assertThat(bookRepository.getBookAuthorsById(firstBook.getId()))
                .doesNotContain(author);
    }

    @Test
    @DirtiesContext
    void shouldCascadeRemoveGenreFromBook() {
        val genre = firstBook.getGenres().get(0);
        genreRepository.deleteById(genre.getId());
        assertThat(bookRepository.getBookGenresById(firstBook.getId()))
                .doesNotContain(genre);
    }
}