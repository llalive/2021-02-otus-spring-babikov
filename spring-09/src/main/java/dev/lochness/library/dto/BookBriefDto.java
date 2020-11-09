package dev.lochness.library.dto;

import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class BookBriefDto {
    private Long id;
    private String title;
    private String authors;

    public static BookBriefDto toDto(Book book){
        return BookBriefDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authors(String.join(", ", book.getAuthors().stream()
                        .map(Author::getFullName)
                        .collect(Collectors.toList())))
                .build();
    }
}
