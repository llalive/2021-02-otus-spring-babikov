package dev.lochness.library.repository;

import dev.lochness.library.domain.Author;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class BookDtoParseTest {

    @Test
    void shouldParseBookFromDtoCorrectly() {
        String names = "Пушкин А.С., Муравьев Алексей Иванович, Толстой";
        List<Author> authors = new ArrayList<Author>();
        Arrays.stream(names.split(","))
                .map(s -> s.trim())
                .forEach(s -> {
                    String[] parts = s.split(" ");
                    String firstName = parts[0];
                    String lastName = parts.length > 1 ?
                            Arrays.stream(parts).skip(1).collect(Collectors.joining()) : "";
                    authors.add(Author.builder().firstName(firstName).lastName(lastName).build());
                });
        System.out.println(authors);
    }
}