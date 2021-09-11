package dev.lochness.library.repository;

import dev.lochness.library.BaseEmbedDatabaseTest;
import dev.lochness.library.domain.Author;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Class AuthorRepositoryJpa")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorRepositoryJpaTest extends BaseEmbedDatabaseTest {

    private static final String TEST_USER_FIRST_NAME = "Sam";
    private static final String TEST_USER_LAST_NAME = "Sepiol";
    private static final long EXPECTED_NEW_AUTHOR_ID = 3L;
    private static final long DEFAULT_AUTHOR_ID = 1L;
    private static final int EXPECTED_NUMBER_OF_AUTHORS = 2;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @DirtiesContext
    void shouldAddAuthorsCorrectly() {
        Author addedAuthor = authorRepository.save(Author.builder()
                .firstName(TEST_USER_FIRST_NAME)
                .lastName(TEST_USER_LAST_NAME)
                .build());
        Author author = authorRepository.getOne(EXPECTED_NEW_AUTHOR_ID);
        assertThat(addedAuthor).isNotNull().isEqualToComparingFieldByField(author);
    }

    @Test
    @DirtiesContext
    void shouldReturnAllAuthors() {
        Author defaultAuthor = authorRepository.getOne(DEFAULT_AUTHOR_ID);
        Author addedAuthor = Author.builder()
                .firstName(TEST_USER_FIRST_NAME)
                .lastName(TEST_USER_LAST_NAME)
                .build();
        authorRepository.save(addedAuthor);
        assertThat(authorRepository.findAll()).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHORS + 1)
                .containsAll(List.of(defaultAuthor, addedAuthor));
    }

    @Test
    void shouldReturnCorrectAuthorById() {
        Optional<Author> author = authorRepository.findById(1L);
        Author expected = authorRepository.getOne(DEFAULT_AUTHOR_ID);
        assertThat(author).isPresent().get().isEqualToComparingFieldByField(expected);
    }
}