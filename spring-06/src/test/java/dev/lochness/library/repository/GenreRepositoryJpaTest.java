package dev.lochness.library.repository;

import dev.lochness.library.domain.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Class GenreRepositoryJpa")
@DataJpaTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJpaTest {

    private static final String TEST_GENRE_NAME = "фэнтези";
    private static final String DEFAULT_GENRE_NAME = "драма";
    private static final long DEFAULT_GENRE_ID = 3L;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    void shouldReturnCorrectNumberOfGenres() {
        assertEquals(8, genreRepository.getGenresCount());
    }

    @Test
    @DirtiesContext
    void shouldAddGenreCorrectly() {
        Genre genre = genreRepository.saveGenre(new Genre(TEST_GENRE_NAME));
        assertEquals(TEST_GENRE_NAME, genre.getName());
    }

    @Test
    @DirtiesContext
    void shouldNotContainGenreAfterDelete() {
        genreRepository.deleteGenreById(5L);
        assertEquals(7, genreRepository.getGenresCount());
    }

    @Test
    void shouldReturnCorrectGenreById() {
        Genre genre = genreRepository.findGenreById(DEFAULT_GENRE_ID);
        assertEquals(DEFAULT_GENRE_NAME, genre.getName());
    }

    @Test
    void shouldReturnAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        assertEquals(8, genres.size());
    }
}