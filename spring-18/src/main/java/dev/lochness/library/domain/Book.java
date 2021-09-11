package dev.lochness.library.domain;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Builder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@NamedEntityGraph(
        name = "book-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("genres"),
                @NamedAttributeNode("comments")
        }
)
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "books_seq_generator")
    @SequenceGenerator(
            name = "books_seq_generator",
            sequenceName = "books_seq",
            allocationSize = 1
    )
    @Column(name = "book_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "isbn", unique = true)
    private String isbn;

    @ManyToMany(targetEntity = Author.class, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "book_authors", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;

    @ManyToMany(targetEntity = Genre.class, fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "book_genres", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @ToString.Exclude
    private List<Genre> genres;

    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 5)
    @OneToMany(targetEntity = Comment.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    @ToString.Exclude
    private List<Comment> comments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return 967762358;
    }
}
