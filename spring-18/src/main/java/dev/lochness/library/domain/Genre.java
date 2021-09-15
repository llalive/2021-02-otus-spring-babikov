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
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genres_seq_generator")
    @SequenceGenerator(
            name = "genres_seq_generator",
            sequenceName = "genres_seq",
            allocationSize = 1
    )
    @Column(name = "genre_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Genre genre = (Genre) o;

        return Objects.equals(id, genre.id);
    }

    @Override
    public int hashCode() {
        return 1887069089;
    }
}