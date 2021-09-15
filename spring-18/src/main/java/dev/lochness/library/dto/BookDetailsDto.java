package dev.lochness.library.dto;

import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Book;
import dev.lochness.library.domain.Comment;
import dev.lochness.library.domain.Genre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class BookDetailsDto {
    private Long id;
    private String title;
    private String authors;
    private String isbn;
    private String genres;
    private List<Comment> comments;

    public static BookDetailsDto toDto(Book book) {
        return BookDetailsDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authors(String.join(", ", book.getAuthors().stream()
                        .map(Author::getFullName)
                        .collect(Collectors.toList())))
                .genres(String.join(", ", book.getGenres().stream().map(Genre::getName)
                        .collect(Collectors.toList())))
                .isbn(book.getIsbn())
                .comments(book.getComments())
                .build();
    }

    public static Book toBook(BookDetailsDto bookDto) {
        return Book.builder()
                .id(bookDto.getId())
                .title(bookDto.getTitle())
                .authors(parseAuthors(bookDto.getAuthors()))
                .genres(Arrays.stream(bookDto.genres.split(",")).map(s ->
                        Genre.builder().name(s.trim()).build()).collect(Collectors.toList()))
                .isbn(bookDto.getIsbn())
                .build();
    }

    private static Set<Author> parseAuthors(String inlineAuthors) {
        Set<Author> authors = new HashSet<>();
        Arrays.stream(inlineAuthors.split(","))
                .map(s -> s.trim())
                .forEach(s -> {
                    String[] parts = s.split(" ");
                    String firstName = parts[0];
                    String lastName = parts.length > 1 ?
                            Arrays.stream(parts).skip(1).collect(Collectors.joining(" ")) : "";
                    authors.add(Author.builder().firstName(firstName).lastName(lastName).build());
                });
        return authors;
    }
}
