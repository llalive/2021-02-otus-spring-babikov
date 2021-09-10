package dev.lochness.library.controller;

import dev.lochness.library.domain.Genre;
import dev.lochness.library.service.LibraryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class GenreContoller {

    private final LibraryService libraryService;

    @GetMapping("/api/genres")
    public List<Genre> listGenres() {
        return libraryService.getGenres();
    }
}
