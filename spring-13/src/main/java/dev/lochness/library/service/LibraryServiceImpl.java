package dev.lochness.library.service;

import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Book;
import dev.lochness.library.domain.Genre;
import dev.lochness.library.dto.BookBriefDto;
import dev.lochness.library.dto.BookDetailsDto;
import dev.lochness.library.repository.AuthorRepository;
import dev.lochness.library.repository.BookRepository;
import dev.lochness.library.repository.GenreRepository;
import dev.lochness.library.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final EhCacheBasedAclCache aclCache;

    @Value("${books-per-page-count}")
    private int booksPerPageCount;

    @Autowired
    protected JdbcMutableAclService mutableAclService;

    @Autowired
    public LibraryServiceImpl(BookRepository bookRepository,
                              GenreRepository genreRepository,
                              AuthorRepository authorRepository,
                              EhCacheBasedAclCache aclCache) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
        this.aclCache = aclCache;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookBriefDto> getBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookBriefDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BookDetailsDto> getBookById(Long id) {
        return bookRepository.findById(id).map(BookDetailsDto::toDto);
    }

    @Override
    @Transactional
    public Book updateOrCreateBook(Book book) {
        book.setGenres(book.getGenres().stream()
                .map(genre -> genreRepository.findByName(genre.getName())
                        .orElse(genre))
                .collect(Collectors.toList()));
        book.setAuthors(book.getAuthors().stream()
                .map(author -> authorRepository.findByFirstNameAndLastName(author.getFirstName(), author.getLastName())
                        .orElse(author))
                .collect(Collectors.toList()));

        boolean isNewBook = book.getId() == null;

        book = bookRepository.save(book);

        if (isNewBook) {
            grantPermissions(book);
        }

        return book;
    }

    private void grantPermissions(Book book) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ObjectIdentity oid = new ObjectIdentityImpl(book.getClass(), book.getId());
        final Sid owner = new PrincipalSid(authentication);
        final Sid user = new GrantedAuthoritySid(new SimpleGrantedAuthority(Role.USER.name()));
        final Sid admin = new GrantedAuthoritySid(new SimpleGrantedAuthority(Role.ADMIN.name()));
        MutableAcl acl = mutableAclService.createAcl(oid);
        if (acl.getEntries().isEmpty()) {
            acl.setOwner(owner);
            acl.insertAce(acl.getEntries().size(), BasePermission.READ, admin, true);
            acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, admin, true);
            acl.insertAce(acl.getEntries().size(), BasePermission.DELETE, admin, true);
            acl.insertAce(acl.getEntries().size(), BasePermission.READ, user, true);
            mutableAclService.updateAcl(acl);
        }
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.getOne(id);
        bookRepository.delete(book);
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
