package dev.lochness.library.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Book;
import dev.lochness.library.domain.Genre;
import dev.lochness.library.dto.BookBriefDto;
import dev.lochness.library.dto.BookDetailsDto;
import dev.lochness.library.repository.AuthorRepository;
import dev.lochness.library.repository.BookRepository;
import dev.lochness.library.repository.GenreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LibraryServiceImpl implements LibraryService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    @Value("${books-per-page-count}")
    private int booksPerPageCount;

    @Autowired
    public LibraryServiceImpl(BookRepository bookRepository,
                              GenreRepository genreRepository,
                              AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(commandKey = "getBooksWithOffset", fallbackMethod = "getBooksListFallback")
    public List<BookBriefDto> getBooksWithOffset(int page) {
        Pageable pageable = PageRequest.of(page, booksPerPageCount,
                Sort.by(Sort.Direction.ASC, "id"));
        return bookRepository.findAll(pageable)
                .get()
                .map(BookBriefDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(
            fallbackMethod = "getBookByIdFallback",
            commandKey = "getBookById",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "4"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            }
    )
    public Optional<BookDetailsDto> getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(BookDetailsDto::toDto);
    }

    @Override
    @Transactional
    @HystrixCommand(fallbackMethod = "updateBookFallback", commandKey = "updateBook")
    public Book updateBook(Book book) {
        List<Genre> genres = book.getGenres().stream().map(genre -> {
            Optional<Genre> genreExisted = genreRepository.findByName(genre.getName());
            return genreExisted.orElse(genre);
        }).collect(Collectors.toList());
        book.setGenres(genres);
        Set<Author> authors = book.getAuthors().stream().map(author -> {
            Optional<Author> authorExisted = authorRepository.findByFirstNameAndLastName(
                    author.getFirstName(), author.getLastName());
            return authorExisted.orElse(author);
        }).collect(Collectors.toSet());
        book.setAuthors(authors);
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value = "1"),
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")
    })
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @HystrixCommand(fallbackMethod = "getGenresFallback", commandKey = "getGenres")
    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }

    @Override
    @HystrixCommand(fallbackMethod = "getAuthorsFallback", commandKey = "getAuthors")
    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    //FALLBACKS

    public List<Genre> getGenresFallback() {
        log.warn("Getting genres takes too long");
        return List.of();
    }

    public List<Author> getAuthorsFallback() {
        log.warn("Getting authors takes too long");
        return List.of();
    }

    public Optional<BookDetailsDto> getBookByIdFallback(Long id) {
        return Optional.of(BookDetailsDto.builder()
                .id(id)
                .title("Н/Д")
                .isbn("Н/Д")
                .build());
    }

    public List<BookBriefDto> getBooksListFallback(int page) {
        log.warn("Got problems while getting books list");
        Pageable pageable = PageRequest.of(page, booksPerPageCount % 2,
                Sort.by(Sort.Direction.ASC, "id"));
        return bookRepository.findAll(pageable)
                .get()
                .map(BookBriefDto::toDto)
                .collect(Collectors.toList());
    }

    public Book updateBookFallback(Book book) {
        log.warn("Unable to update book");
        return book;
    }

}
