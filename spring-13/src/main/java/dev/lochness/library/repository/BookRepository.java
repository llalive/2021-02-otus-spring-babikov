package dev.lochness.library.repository;

import dev.lochness.library.domain.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @PostFilter("hasPermission(filterObject, 'READ')")
    List<Book> findAll();

    @PostAuthorize("hasPermission(#id, 'dev.lochness.library.domain.Book', 'READ')")
    Optional<Book> findById(@Param("id") Long id);

    @EntityGraph(attributePaths = {"genres", "authors"})
    @PostAuthorize("hasPermission(#id, 'dev.lochness.library.domain.Book', 'READ')")
    Optional<Book> findBookWithAuthorsAndGenresById(@Param("id") Long id);

    @PreAuthorize("hasAuthority('ADMIN')")
    Book save(Book book);

    @PreAuthorize("hasPermission(#book.id, 'dev.lochness.library.domain.Book', 'DELETE')")
    void delete(@Param("book") Book book);
}