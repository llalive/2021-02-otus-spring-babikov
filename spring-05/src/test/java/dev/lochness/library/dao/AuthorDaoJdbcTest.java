package dev.lochness.library.dao;

import dev.lochness.library.domain.Author;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("Class AuthorDaoJdbc")
class AuthorDaoJdbcTest {

    private static final String TEST_USER_FIRST_NAME = "Sam";
    private static final String TEST_USER_LAST_NAME = "Sepiol";
    private static final String DEFAULT_USER_FIRST_NAME = "Томас";
    private static final String DEFAULT_USER_LAST_NAME = "Харрис";

    @Autowired
    private AuthorDao dao;

    @Test
    void shouldReturnCorrectNumberOfAuthors() {
        assertEquals(1, dao.count());
    }

    @Test
    @DirtiesContext
    void shouldAddAuthorsCorrectly() {
        dao.add(new Author(2, TEST_USER_FIRST_NAME, TEST_USER_LAST_NAME));
        Author author = dao.getById(2);
        assertAll("Should add authors correctly",
                () -> assertEquals(TEST_USER_FIRST_NAME, author.getFirstName()),
                () -> assertEquals(TEST_USER_LAST_NAME, author.getLastName())
        );

    }

    @Test
    @DirtiesContext
    void shouldNotContainAuthorAfterDelete() {
        dao.deleteById(1);
        assertEquals(0, dao.count());
    }

    @Test
    @DirtiesContext
    void shouldReturnAllAuthors() {
        dao.add(new Author(2, TEST_USER_FIRST_NAME, TEST_USER_LAST_NAME));
        assertEquals(2, dao.getAll().size());
    }

    @Test
    void shouldReturnCorrectAuthorById() {
        Author author = dao.getById(1);
        assertAll("Should return correct author",
                () -> assertEquals(DEFAULT_USER_FIRST_NAME, author.getFirstName()),
                () -> assertEquals(DEFAULT_USER_LAST_NAME, author.getLastName())
        );
    }

    @Test
    void shouldReturnCorrectListOfAuthorsForBook() {
        List<Author> authors = dao.getAuthorsForBook(1);
        assertEquals(DEFAULT_USER_FIRST_NAME, authors.get(0).getFirstName());
    }
}