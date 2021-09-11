package dev.lochness.library.controller;

import dev.lochness.library.domain.Author;
import dev.lochness.library.service.LibraryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class AuthorController {

    private final LibraryService libraryService;

    @GetMapping("/api/authors")
    public List<Author> listAuthors() {
        return libraryService.getAuthors();
    }
}
