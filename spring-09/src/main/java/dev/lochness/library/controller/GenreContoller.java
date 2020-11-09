package dev.lochness.library.controller;

import dev.lochness.library.service.LibraryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class GenreContoller {

    private final LibraryService libraryService;

    @GetMapping("/genre/")
    public String listGenres(Model model){
        model.addAttribute("genres", libraryService.getGenres());
        return "genres";
    }
}
