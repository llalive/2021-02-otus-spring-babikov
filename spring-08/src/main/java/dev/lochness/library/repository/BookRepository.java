package dev.lochness.library.repository;

import dev.lochness.library.domain.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String>, BookReporitoryCustom {
    Optional<Book> findBookByTitle(String title);
}