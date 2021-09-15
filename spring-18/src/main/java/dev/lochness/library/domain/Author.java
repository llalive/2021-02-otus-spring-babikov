package dev.lochness.library.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authors_seq_generator")
    @SequenceGenerator(
            name = "authors_seq_generator",
            sequenceName = "authors_seq",
            allocationSize = 1
    )
    @Column(name = "author_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    public String getFullName() {
        return String.join(" ", firstName, lastName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Author author = (Author) o;

        return Objects.equals(id, author.id);
    }

    @Override
    public int hashCode() {
        return 556590234;
    }
}
