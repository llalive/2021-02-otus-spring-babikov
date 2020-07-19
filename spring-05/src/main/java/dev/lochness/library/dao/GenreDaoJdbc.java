package dev.lochness.library.dao;

import dev.lochness.library.domain.Genre;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbc;

    public GenreDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public int count() {
        return jdbc.getJdbcOperations().queryForObject("SELECT count(genreId) FROM Genres",
                Integer.class);
    }

    @Override
    public void add(Genre genre) {
        jdbc.update("INSERT INTO Genres (Name) VALUES (:name)",
                Map.of("name", genre.getName()));
    }

    @Override
    public void deleteById(Integer id) {
        jdbc.update("DELETE FROM Genres WHERE GenreId = :id",
                Map.of("id", id));
    }

    @Override
    public Genre getById(Integer id) {
        return jdbc.queryForObject("SELECT genreId, name FROM Genres WHERE GenreId = :id",
                Map.of("id", id), new GenreMapper());
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("SELECT genreId, name FROM Genres", new GenreMapper());
    }

    @Override
    public List<Genre> getGenresForBook(long bookId) {
        return jdbc.query("SELECT g.genreId, g.name FROM Genres g RIGHT JOIN BookGenres bg " +
                        "ON g.genreId = bg.genreId " +
                        "WHERE bg.bookId = :bookId",
                Map.of("bookId", bookId), new GenreMapper());
    }

    private static final class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            int id = resultSet.getInt("genreId");
            String name = resultSet.getString("name");
            return new Genre(id, name);
        }
    }
}
