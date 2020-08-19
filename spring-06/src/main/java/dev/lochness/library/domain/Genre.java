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
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id", nullable = false, unique = true)
    private long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @ManyToMany(mappedBy = "genres", targetEntity = Book.class)
    private List<Book> books;

    public Genre(String name) {
        this.id = 0;
        this.name = name;
        this.books = new ArrayList<>();
    }
}