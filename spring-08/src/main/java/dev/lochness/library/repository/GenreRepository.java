package dev.lochness.library.repository;

import dev.lochness.library.domain.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GenreRepository extends MongoRepository<Genre, String> {

}
