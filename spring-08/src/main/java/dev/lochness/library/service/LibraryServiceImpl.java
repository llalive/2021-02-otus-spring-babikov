package dev.lochness.library.service;

import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Book;
import dev.lochness.library.domain.Comment;
import dev.lochness.library.domain.Genre;
import dev.lochness.library.io.Printer;
import dev.lochness.library.repository.AuthorRepository;
import dev.lochness.library.repository.BookRepository;
import dev.lochness.library.repository.CommentRepository;
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
    private final CommentRepository commentRepository;

    @Autowired
    public LibraryServiceImpl(Printer printer, BookRepository bookRepository,
                              GenreRepository genreRepository, AuthorRepository authorRepository,
                              CommentRepository commentRepository) {
        this.printer = printer;
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public void listBooks() {
        bookRepository.findAll().forEach(
                book -> printer.printBookInfo(book, false));
    }

    @Override
    @Transactional(readOnly = true)
    public void printBookInfo(String bookId) {
        bookRepository.findById(bookId).ifPresent(
                book -> printer.printBookInfo(book, true));
        printer.printComments(commentRepository.findAllByBookId(bookId));
    }

    @Override
    @Transactional(readOnly = true)
    public void listAuthors() {
        authorRepository.findAll().forEach(
                author -> printer.printAuthor(author));
    }

    @Override
    @Transactional(readOnly = true)
    public void listGenres() {
        genreRepository.findAll().forEach(
                genre -> printer.printGenre(genre));
    }

    @Override
    @Transactional
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteBook(String id) {
        bookRepository.deleteById(id);
        commentRepository.deleteCommentByBookId(id);
    }

    @Override
    @Transactional
    public void setBookGenres(String bookId, List<String> genresId) {
        var book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            var bookValue = book.get();
            bookValue.setGenres((List<Genre>) genreRepository.findAllById(genresId));
            bookRepository.save(bookValue);
        }
    }

    @Override
    @Transactional
    public void setBookAuthors(String bookId, List<String> authorsId) {
        var book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            var bookValue = book.get();
            bookValue.setAuthors((List<Author>) authorRepository.findAllById(authorsId));
            bookRepository.save(bookValue);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void printBooksByGenre(String genreId) {
        genreRepository.findById(genreId).ifPresent(
                value -> printer.printGenre(value));
    }

    @Override
    @Transactional(readOnly = true)
    public void printBooksByAuthor(String authorId) {
        authorRepository.findById(authorId).ifPresent(
                value -> printer.printAuthor(value));
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
    public void deleteGenre(String genreId) {
        genreRepository.deleteById(genreId);
    }

    @Override
    @Transactional
    public void deleteAuthor(String authorId) {
        authorRepository.deleteById(authorId);
    }

    @Override
    @Transactional
    public void updateBook(String bookId, String title, String isbn) {
        var book = bookRepository.findById(bookId);
        var value = book.get();
        value.setTitle(title);
        value.setIsbn(isbn);
        bookRepository.save(value);
    }

    @Override
    @Transactional
    public void addComment(String bookId, Comment comment) {
        var book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            comment.setBookId(bookId);
            commentRepository.save(comment);
        }
    }

}
