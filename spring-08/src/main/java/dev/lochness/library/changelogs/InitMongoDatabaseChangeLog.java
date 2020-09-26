package dev.lochness.library.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Book;
import dev.lochness.library.domain.Comment;
import dev.lochness.library.domain.Genre;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;
import java.util.Collections;

@ChangeLog(order = "000")
public class InitMongoDatabaseChangeLog {

    private Author firstAuthor;
    private Author secondAuthor;
    private Genre firstGenre;
    private Genre secondGenre;
    private Genre thirdGenre;
    private Book firstBook;
    private Book secondBook;

    @ChangeSet(order = "000", id = "dropDatabase", author = "llalive", runAlways = true)
    public void dropDatabase(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "llalive", runAlways = true)
    public void initAuthors(MongoTemplate template) {
        firstAuthor = template.save(Author.builder()
                .firstName("Анджей")
                .lastName("Сапковский")
                .build());
        secondAuthor = template.save(Author.builder()
                .firstName("Борис")
                .lastName("Акунин")
                .build());
    }

    @ChangeSet(order = "002", id = "initGenres", author = "llalive", runAlways = true)
    public void initGenres(MongoTemplate template) {
        firstGenre = template.save(Genre.builder().name("Историческая литература").build());
        secondGenre = template.save(Genre.builder().name("Героическое фэнтези").build());
        thirdGenre = template.save(Genre.builder().name("Зарубежное фэнтези").build());
    }

    @ChangeSet(order = "003", id = "initBooks", author = "llalive", runAlways = true)
    public void initBooks(MongoTemplate template) {
        firstBook = template.save(Book.builder()
                .authors(Collections.singletonList(firstAuthor))
                .genres(Arrays.asList(secondGenre, thirdGenre))
                .title("Ведьмак")
                .isbn("978-5-271-40351-4")
                .build());
        secondBook = template.save(Book.builder()
                .authors(Collections.singletonList(secondAuthor))
                .genres(Collections.singletonList(firstGenre))
                .title("Мир и война")
                .isbn("978-5-17-082578-3")
                .build());
    }

    @ChangeSet(order = "004", id = "initComments", author = "llalive", runAlways = true)
    public void initComments(MongoTemplate template) {
        template.save(Comment.builder()
                .text("Я прочитал! Пушка-бомба!")
                .commentedBy("Анатолий Вассерман")
                .bookId(firstBook.getId())
                .build());
        template.save(Comment.builder()
                .text("Я лучше пишу...")
                .commentedBy("Анатолий Вассерман")
                .bookId(secondBook.getId())
                .build());
    }

}
