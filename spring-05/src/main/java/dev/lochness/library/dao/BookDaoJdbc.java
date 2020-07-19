package dev.lochness.library.dao;

import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Book;
import dev.lochness.library.domain.Genre;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations jdbc;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public int count() {
        return jdbc.getJdbcOperations().queryForObject("SELECT COUNT(bookId) FROM Books",
                Integer.class);
    }

    @Override
    public void add(Book book) {
        jdbc.update("INSERT INTO Books (title, ISBN) VALUES (:title, :isbn)",
                Map.of("title", book.getTitle(), "isbn", book.getISBN()));
    }

    @Override
    public void update(Book book) {
        jdbc.update("UPDATE Books SET title = :title, ISBN = :isbn WHERE bookId = :id",
                Map.of("title", book.getTitle(),
                        "isbn", book.getISBN(),
                        "id", book.getId()));
        if (book.getAuthors() != null) {
            jdbc.update("DELETE FROM BookAuthors WHERE bookId = :book",
                    Map.of("book", book.getId()));
            for (Author author : book.getAuthors()) {
                jdbc.update("INSERT INTO BookAuthors (bookId, authorId) " +
                                "VALUES (:book, :author)",
                        Map.of("book", book.getId(), "author", author.getId()));
            }
        }
        if (book.getGenres() != null) {
            jdbc.update("DELETE FROM BookGenres WHERE bookId = :book",
                    Map.of("book", book.getId()));
            for (Genre genre : book.getGenres()) {
                jdbc.update("INSERT INTO BookGenres (bookId, genreId) " +
                                "VALUES (:book, :genre)",
                        Map.of("book", book.getId(), "genre", genre.getId()));
            }
        }
    }

    @Override
    public void deleteById(long id) {
        jdbc.update("DELETE FROM Books WHERE bookId = :id",
                Map.of("id", id));
    }

    @Override
    public Book getById(long id) {
        return jdbc.queryForObject("SELECT bookId, title, ISBN FROM Books WHERE bookId = :id",
                Map.of("id", id), new BookMapper());
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("SELECT bookId, title, ISBN FROM Books", new BookMapper());
    }

    @Override
    public List<Book> getByAuthorId(long authorId) {
        return jdbc.query("SELECT b.bookId, b.title, b.ISBN FROM Books b RIGHT JOIN BookAuthors ba " +
                "ON b.BookId = ba.BookId " +
                "WHERE ba.AuthorId = :author", Map.of("author", authorId), new BookMapper());
    }

    @Override
    public List<Book> getByGenre(int genreId) {
        return jdbc.query("SELECT b.bookId, b.title, b.ISBN FROM Books b RIGHT JOIN BookGenres bg " +
                "ON b.BookId = bg.BookId " +
                "WHERE bg.GenreId = :genre", Map.of("genre", genreId), new BookMapper());
    }

    @Override
    public List<Book> search(String substring) {
        return jdbc.query("SELECT bookId, title, ISBN FROM Books WHERE title LIKE '%'||:title||'%'",
                Map.of("title", substring), new BookMapper());
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("bookId");
            String title = resultSet.getString("title");
            String ISBN = resultSet.getString("ISBN");
            return new Book(id, title, ISBN);
        }
    }
}
