package dev.lochness.library.repository;

import dev.lochness.library.domain.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Class GenreRepositoryJpa")
@DataJpaTest
class GenreRepositoryJpaTest {

    private static final String TEST_GENRE_NAME = "фэнтези";
    private static final String DEFAULT_GENRE_NAME = "драма";
    private static final long DEFAULT_GENRE_ID = 3L;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    @DirtiesContext
    void shouldAddGenreCorrectly() {
        Genre genre = genreRepository.save(Genre.builder()
                .name(TEST_GENRE_NAME)
                .build());
        assertEquals(TEST_GENRE_NAME, genre.getName());
    }

    @Test
    void shouldReturnCorrectGenreById() {
        assertThat(genreRepository.findById(DEFAULT_GENRE_ID)).isPresent()
                .get().hasFieldOrPropertyWithValue("name", DEFAULT_GENRE_NAME);
    }

    @Test
    void shouldReturnAllGenres() {
        assertEquals(8, genreRepository.findAll().size());
    }
}