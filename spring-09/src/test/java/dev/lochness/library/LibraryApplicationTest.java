package dev.lochness.library;

import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Book;
import dev.lochness.library.domain.Genre;
import dev.lochness.library.repository.AuthorRepository;
import dev.lochness.library.repository.BookRepository;
import dev.lochness.library.repository.GenreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LibraryApplicationTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void shouldNotRemoveAuthorsAfterBookRemoved() {
        long authorsCount = authorRepository.count();
        Book book = bookRepository.save(getBook());
        bookRepository.deleteById(book.getId());
        assertEquals(authorsCount + 1, authorRepository.count());
    }

    @Test
    public void shouldNotRemoveGenresAfterBookRemoved() {
        long genresCount = genreRepository.count();
        Book book = bookRepository.save(getBook());
        bookRepository.deleteById(book.getId());
        assertEquals(genresCount + 1, genreRepository.count());
    }

    private Book getBook() {
        ArrayList<Genre> genres = new ArrayList<>();
        genres.add(getGenre());
        ArrayList<Author> authors = new ArrayList<>();
        authors.add(getAuthor());
        return Book.builder()
                .title("Title")
                .genres(genres)
                .authors(authors)
                .build();
    }

    private Author getAuthor() {
        return Author.builder()
                .firstName("First")
                .lastName("Last")
                .build();
    }

    private Genre getGenre() {
        return Genre.builder()
                .name("Genre")
                .build();
    }
}
