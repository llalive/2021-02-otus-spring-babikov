package dev.lochness.library.repository;

import dev.lochness.library.domain.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findAllByBookId(String bookId);

    void deleteCommentByBookId(String bookId);
}
