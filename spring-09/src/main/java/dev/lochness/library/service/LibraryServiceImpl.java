package dev.lochness.library.service;

import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Book;
import dev.lochness.library.domain.Genre;
import dev.lochness.library.dto.BookBriefDto;
import dev.lochness.library.dto.BookDetailsDto;
import dev.lochness.library.repository.AuthorRepository;
import dev.lochness.library.repository.BookRepository;
import dev.lochness.library.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    @Value("${books-per-page-count}")
    private final int BOOKS_PER_PAGE_COUNT = 5;

    @Autowired
    public LibraryServiceImpl(BookRepository bookRepository,
                              GenreRepository genreRepository,
                              AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<BookBriefDto> getBooksWithOffset(int offset) {
        Pageable pageable = PageRequest.of(offset, offset + BOOKS_PER_PAGE_COUNT,
                Sort.by(Sort.Direction.ASC, "id"));
        return bookRepository.findAll(pageable)
                .get()
                .map(BookBriefDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BookDetailsDto> getBookById(Long id) {
        return bookRepository.findById(id).map(BookDetailsDto::toDto);
    }

    @Override
    public Book updateBook(Book book) {
        List<Genre> genres = book.getGenres().stream().map(genre -> {
            Optional<Genre> genreExisted = genreRepository.findByName(genre.getName());
            return genreExisted.orElse(genre);
        }).collect(Collectors.toList());
        book.setGenres(genres);
        List<Author> authors = book.getAuthors().stream().map(author -> {
            Optional<Author> authorExisted = authorRepository.findByFirstNameAndLastName(
                    author.getFirstName(), author.getLastName());
            return authorExisted.orElse(author);
        }).collect(Collectors.toList());
        book.setAuthors(authors);
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }

    @Override
    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    /*@Autowired
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
    }*/

}
