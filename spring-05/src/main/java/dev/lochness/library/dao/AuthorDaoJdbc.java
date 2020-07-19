package dev.lochness.library.dao;

import dev.lochness.library.domain.Author;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations jdbc;

    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public int count() {
        return jdbc.getJdbcOperations().queryForObject("SELECT count(authorId) FROM Authors",
                Integer.class);
    }

    @Override
    public void add(Author author) {
        jdbc.update("INSERT INTO Authors (firstName, lastName) VALUES (:firstName, :lastName)",
                Map.of("firstName", author.getFirstName(),
                        "lastName", author.getLastName()));
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("DELETE FROM Authors WHERE authorId = :id",
                Map.of("id", id));
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("SELECT authorId, firstName, lastName FROM Authors", new AuthorMapper());
    }

    @Override
    public Author getById(long id) {
        return jdbc.queryForObject("SELECT authorId, firstName, lastName FROM Authors WHERE authorId = :id",
                Map.of("id", id), new AuthorMapper());
    }

    @Override
    public List<Author> getAuthorsForBook(long bookId) {
        return jdbc.query("SELECT a.authorId, a.firstName, a.lastName FROM Authors a RIGHT JOIN BookAuthors ba " +
                        "ON a.authorId = ba.authorId " +
                        "WHERE ba.bookId = :bookId",
                Map.of("bookId", bookId), new AuthorMapper());
    }

    private static final class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("authorId");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            return new Author(id, firstName, lastName);
        }
    }
}
