package dev.lochness.library.dao;

import dev.lochness.library.domain.Genre;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("Class GenreDaoJdbc")
class GenreDaoJdbcTest {

    private static final String TEST_GENRE_NAME = "фэнтези";
    private static final String DEFAULT_GENRE_NAME = "драма";
    private static final int DEFAULT_GENRE_ID = 3;

    @Autowired
    private GenreDao dao;

    @Test
    void shouldReturnCorrectNumberOfGenres() {
        assertEquals(8, dao.count());
    }

    @Test
    @DirtiesContext
    void shouldAddGenreCorrectly() {
        dao.add(new Genre(TEST_GENRE_NAME));
        Genre genre = dao.getById(9);
        assertEquals(TEST_GENRE_NAME, genre.getName());
    }

    @Test
    @DirtiesContext
    void shouldNotContainGenreAfterDelete() {
        dao.deleteById(5);
        assertEquals(7, dao.count());
    }

    @Test
    void shouldReturnCorrectGenreById() {
        Genre genre = dao.getById(DEFAULT_GENRE_ID);
        assertEquals(DEFAULT_GENRE_NAME, genre.getName());
    }

    @Test
    void shouldReturnAllGenres() {
        List<Genre> genres = dao.getAll();
        assertEquals(8, genres.size());
    }

    @Test
    void shouldReturnCorrectGenresForBook() {
        List<Genre> genres = dao.getGenresForBook(1);
        assertEquals(2, genres.size());
    }
}