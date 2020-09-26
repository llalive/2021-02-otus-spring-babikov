package dev.lochness.library.repository;

import dev.lochness.library.domain.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthorRepository extends MongoRepository<Author, String> {

}
