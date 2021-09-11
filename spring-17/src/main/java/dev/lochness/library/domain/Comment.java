package dev.lochness.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMENTS_seq_generator")
    @SequenceGenerator(
            name = "COMMENTS_seq_generator",
            sequenceName = "COMMENTS_seq",
            allocationSize = 1
    )
    @Column(name = "comment_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "commented_by")
    private String commentedBy;

    @Column(name = "text", nullable = false)
    private String text;
}
