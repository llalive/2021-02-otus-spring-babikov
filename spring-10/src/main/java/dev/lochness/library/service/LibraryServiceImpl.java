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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<BookBriefDto> getBooksWithOffset(int page) {
        Pageable pageable = PageRequest.of(page, booksPerPageCount,
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
    @Transactional
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

}
