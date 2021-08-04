package dev.lochness.library.service;

import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Book;
import dev.lochness.library.domain.Comment;
import dev.lochness.library.domain.Genre;
import dev.lochness.library.dto.BookBriefDto;
import dev.lochness.library.repository.AuthorRepository;
import dev.lochness.library.repository.BookRepository;
import dev.lochness.library.repository.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DirtiesContext
@SpringBootTest
@ExtendWith(SpringExtension.class)
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
    @WithMockUser(authorities = {"ADMIN"})
    @Transactional
    @DirtiesContext
    void shouldAddBookCorrectly() {
        Book book = Book.builder()
                .title("Title")
                .isbn("01234567-89-10")
                .genres(new ArrayList<>(List.of(Genre.builder()
                                .name("Genre 1")
                                .build(),
                        Genre.builder()
                                .name("Genre 2")
                                .build())))
                .authors(new ArrayList<>(List.of(Author.builder()
                                .firstName("Firstname 1")
                                .lastName("Lastname 1")
                                .build(),
                        Author.builder()
                                .firstName("Firstname 2")
                                .lastName("Lastname 2")
                                .build())))
                .comments(new ArrayList<>(List.of(Comment.builder()
                        .commentedBy("Commentor")
                        .text("Comment")
                        .build())))
                .build();
        Book savedBook = service.updateOrCreateBook(book);
        Book actual = bookRepository.findById(savedBook.getId()).orElseThrow();
        assertThat(actual).isEqualToComparingFieldByField(book);
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void shouldRemoveBookCorrectly() {
        Book newBook = service.updateOrCreateBook(buildBook());
        service.deleteBook(newBook.getId());
        Optional<Book> expected = bookRepository.findById(newBook.getId());
        Assertions.assertTrue(expected.isEmpty());
    }

    @Test
    void shouldReturnAllGenres() {
        Assertions.assertArrayEquals(genreRepository.findAll().toArray(),
                service.getGenres().toArray());
    }

    @Test
    void shouldReturnAllAuthors() {
        Assertions.assertArrayEquals(authorRepository.findAll().toArray(),
                service.getAuthors().toArray());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void shouldReturnAllAvailableBooksForAdmin() {
        List<BookBriefDto> actual = service.getBooks();
        assertThat(actual).hasSize(3);
    }

    @Test
    @WithMockUser(authorities = {"GUEST"})
    void shouldReturnNoBooksForGuest() {
        List<BookBriefDto> actual = service.getBooks();
        assertThat(actual).isEmpty();
    }


    @Test
    @WithMockUser(authorities = {"GUEST"})
    void shouldDenyAccessToBookForGuest() {
        assertThrows(AccessDeniedException.class, () -> service.getBookById(3L));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void shouldAllowAdminToAddBook() {
        Book savedBook = service.updateOrCreateBook(buildBook());
        assertThat(savedBook).isNotNull().hasFieldOrProperty("id").isNotNull();
    }

    private Book buildBook() {
        return Book.builder()
                .title("Great Book")
                .authors(Collections.singletonList(Author.builder()
                        .firstName("Alex")
                        .lastName("Butov")
                        .build()))
                .genres(Collections.singletonList(Genre.builder()
                        .name("Fantasy")
                        .build()))
                .build();
    }
}