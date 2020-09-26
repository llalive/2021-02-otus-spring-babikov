package dev.lochness.library.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
public class Book {

    @Id
    private String id;
    private String title;
    private String isbn;
    @DBRef
    private List<Author> authors;
    @DBRef
    private List<Genre> genres;
}
