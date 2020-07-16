package dev.lochness.library.service;

import dev.lochness.library.dao.AuthorDao;
import dev.lochness.library.dao.BookDao;
import dev.lochness.library.dao.GenreDao;
import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Book;
import dev.lochness.library.domain.Genre;
import dev.lochness.library.io.Printer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final Printer printer;
    private final BookDao bookDao;
    private final GenreDao genreDao;
    private final AuthorDao authorDao;

    @Autowired
    public LibraryServiceImpl(Printer printer, BookDao bookDao,
                              GenreDao genreDao, AuthorDao authorDao) {
        this.printer = printer;
        this.bookDao = bookDao;
        this.genreDao = genreDao;
        this.authorDao = authorDao;
    }

    @Override
    public void listBooks() {
        printBooks(bookDao.getAll());
    }

    @Override
    public void printBookInfo(long bookId) {
        Book book = bookDao.getById(bookId);
        book.setAuthors(authorDao.getAuthorsForBook(bookId));
        book.setGenres(genreDao.getGenresForBook(bookId));
        printer.printBookInfo(book);
    }

    @Override
    public void listAuthors() {
        List<Author> authors = authorDao.getAll();
        for (Author author : authors) {
            printer.printAuthor(author);
        }
    }

    @Override
    public void listGenres() {
        List<Genre> genres = genreDao.getAll();
        for (Genre genre : genres) {
            printer.printGenre(genre);
        }
    }

    @Override
    public void addBook(Book book) {
        bookDao.add(book);
    }

    @Override
    public void deleteBook(long id) {
        bookDao.deleteById(id);
    }

    @Override
    public void setBookGenres(long bookId, List<Integer> genresId) {
        Book book = bookDao.getById(bookId);
        List<Genre> genres = new ArrayList<>();
        for (Integer genreId : genresId) {
            Genre genre = genreDao.getById(genreId);
            genres.add(genre);
        }
        book.setGenres(genres);
        bookDao.update(book);
    }

    @Override
    public void setBookAuthors(long bookId, List<Long> authorsId) {
        Book book = bookDao.getById(bookId);
        List<Author> authors = new ArrayList<>();
        for (Long authorId : authorsId) {
            Author author = authorDao.getById(authorId);
            authors.add(author);
        }
        book.setAuthors(authors);
        bookDao.update(book);
    }

    @Override
    public void printBooksByGenre(int genreId) {
        Genre genre = genreDao.getById(genreId);
        genre.setBooks(bookDao.getByGenre(genreId));
        printer.printGenre(genre);
    }

    @Override
    public void printBooksByAuthor(long authorId) {
        Author author = authorDao.getById(authorId);
        author.setBooks(bookDao.getByAuthorId(authorId));
        printer.printAuthor(author);
    }

    @Override
    public void addGenre(Genre genre) {
        genreDao.add(genre);
    }

    @Override
    public void addAuthor(Author author) {
        authorDao.add(author);
    }

    @Override
    public void deleteGenre(int genreId) {
        genreDao.deleteById(genreId);
    }

    @Override
    public void deleteAuthor(long authorId) {
        authorDao.deleteById(authorId);
    }

    @Override
    public void updateBook(long bookId, String title, String isbn) {
        bookDao.update(new Book(bookId, title, isbn));
    }

    private void printBooks(List<Book> books) {
        if (!books.isEmpty()) {
            for (Book book : books) {
                printer.printBookInfo(book);
            }
        }
    }
}
