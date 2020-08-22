package dev.lochness.library.service;

import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Book;
import dev.lochness.library.domain.Comment;
import dev.lochness.library.domain.Genre;
import dev.lochness.library.io.Printer;
import dev.lochness.library.repository.AuthorRepository;
import dev.lochness.library.repository.BookRepository;
import dev.lochness.library.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final Printer printer;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public LibraryServiceImpl(Printer printer, BookRepository bookRepository,
                              GenreRepository genreRepository, AuthorRepository authorRepository) {
        this.printer = printer;
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public void listBooks() {
        bookRepository.findAll().forEach(
                book -> printer.printBookInfo(book, false));
    }

    @Override
    @Transactional(readOnly = true)
    public void printBookInfo(long bookId) {
        bookRepository.findById(bookId).ifPresent(
                book -> printer.printBookInfo(book, true));
    }

    @Override
    @Transactional(readOnly = true)
    public void listAuthors() {
        authorRepository.findAll().forEach(
                author -> printer.printAuthor(author, false));
    }

    @Override
    @Transactional(readOnly = true)
    public void listGenres() {
        genreRepository.findAll().forEach(
                genre -> printer.printGenre(genre, false));
    }

    @Override
    @Transactional
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteBook(long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void setBookGenres(long bookId, List<Long> genresId) {
        var book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            var bookValue = book.get();
            bookValue.setGenres(genreRepository.findAllById(genresId));
            bookRepository.save(bookValue);
        }
    }

    @Override
    @Transactional
    public void setBookAuthors(long bookId, List<Long> authorsId) {
        var book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            var bookValue = book.get();
            bookValue.setAuthors(authorRepository.findAllById(authorsId));
            bookRepository.save(bookValue);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void printBooksByGenre(long genreId) {
        genreRepository.findById(genreId).ifPresent(
                value -> printer.printGenre(value, true));
    }

    @Override
    @Transactional(readOnly = true)
    public void printBooksByAuthor(long authorId) {
        authorRepository.findById(authorId).ifPresent(
                value -> printer.printAuthor(value, true));
    }

    @Override
    @Transactional
    public Genre addGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    @Transactional
    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public void deleteGenre(long genreId) {
        genreRepository.deleteById(genreId);
    }

    @Override
    @Transactional
    public void deleteAuthor(long authorId) {
        authorRepository.deleteById(authorId);
    }

    @Override
    @Transactional
    public void updateBook(long bookId, String title, String isbn) {
        var book = bookRepository.findById(bookId);
        var value = book.get();
        value.setTitle(title);
        value.setIsbn(isbn);
        bookRepository.save(value);
    }

    @Override
    @Transactional
    public void addComment(long bookId, Comment comment) {
        var book = bookRepository.findById(bookId);
        if(book.isPresent()){
            var value = book.get();
            value.getComments().add(comment);
            bookRepository.save(value);
        }
    }

}
