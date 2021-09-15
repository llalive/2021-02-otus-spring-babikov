package dev.lochness.library.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AUTHORS_seq_generator")
    @SequenceGenerator(
            name = "AUTHORS_seq_generator",
            sequenceName = "AUTHORS_seq",
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
}
