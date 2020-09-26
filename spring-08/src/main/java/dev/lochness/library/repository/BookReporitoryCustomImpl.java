package dev.lochness.library.repository;

import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Book;
import dev.lochness.library.domain.Genre;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.ObjectOperators.ObjectToArray.valueOfToArray;

@RequiredArgsConstructor
public class BookReporitoryCustomImpl implements BookReporitoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public void removeGenreArrayElementsById(String id) {
        mongoTemplate.updateMulti(new Query(),
                new Update().pull("genres", getQueryForId(id)), Book.class);
    }

    @Override
    public void removeAuthorArrayElementsById(String id) {
        mongoTemplate.updateMulti(new Query(),
                new Update().pull("authors", getQueryForId(id)), Book.class);
    }

    @Override
    public List<Author> getBookAuthorsById(String bookId) {
        val aggregation = newAggregation(
                match(Criteria.where("id").is(bookId)),
                unwind("authors"),
                project().andExclude("_id").and(valueOfToArray("authors")).as("authors_map"),
                        project().and("authors_map").arrayElementAt(1).as("authors_id_map"),
                        project().and("authors_id_map.v").as("authors_id"),
                        lookup("authors", "authors_id", "_id", "authors"),
                        project().and("authors._id").as("_id")
                                .and("authors.firstName").as("firstName")
                                .and("authors.lastName").as("lastName")
        );
        return mongoTemplate.aggregate(aggregation, Book.class, Author.class).getMappedResults();
    }

    @Override
    public List<Genre> getBookGenresById(String bookId) {
        val aggregation = newAggregation(
                match(Criteria.where("id").is(bookId)),
                unwind("genres"),
                project().andExclude("_id").and(valueOfToArray("genres")).as("genres_map"),
                        project().and("genres_map").arrayElementAt(1).as("genres_id_map"),
                        project().and("genres_id_map.v").as("genres_id"),
                        lookup("genres", "genres_id", "_id", "genres"),
                        project().and("genres._id").as("_id")
                                .and("genres.name").as("name")
        );
        return mongoTemplate.aggregate(aggregation, Book.class, Genre.class).getMappedResults();
    }

    private Query getQueryForId(String id) {
        return Query.query(Criteria.where("$id").is(new ObjectId(id)));
    }
}
