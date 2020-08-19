package dev.lochness.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id", nullable = false, unique = true)
    private long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @ManyToMany(mappedBy = "authors", targetEntity = Book.class)
    private List<Book> books;

    public Author(String firstName, String lastName) {
        this.id = 0L;
        this.firstName = firstName;
        this.lastName = lastName;
        this.books = new ArrayList<>();
    }
}
