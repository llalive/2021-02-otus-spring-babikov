package dev.lochness.library.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comments_seq_generator")
    @SequenceGenerator(
            name = "comments_seq_generator",
            sequenceName = "comments_seq",
            allocationSize = 1
    )
    @Column(name = "comment_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "commented_by")
    private String commentedBy;

    @Column(name = "text", nullable = false)
    private String text;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Comment comment = (Comment) o;

        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return 860659860;
    }
}
