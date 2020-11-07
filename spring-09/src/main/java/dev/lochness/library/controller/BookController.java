package dev.lochness.library.controller;

import dev.lochness.library.domain.Book;
import dev.lochness.library.dto.BookDetailsDto;
import dev.lochness.library.exceptions.NotFoundException;
import dev.lochness.library.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookController {

    private final LibraryService libraryService;

    @Autowired
    public BookController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/")
    public String listBooks(@RequestParam(value = "offset", defaultValue = "0") int offset, Model model) {
        model.addAttribute("books", libraryService.getBooksWithOffset(offset));
        return "books";
    }

    @GetMapping("/book/{bookId}")
    public String getBookDetails(@PathVariable Long bookId, Model model) {
        BookDetailsDto book = libraryService.getBookById(bookId).orElseThrow(NotFoundException::new);
        model.addAttribute("book", book);
        return "details";
    }

    @GetMapping("/book/{bookId}/edit")
    public String getBookEditPage(@PathVariable Long bookId, Model model) {
        BookDetailsDto book = libraryService.getBookById(bookId).orElseThrow(NotFoundException::new);
        model.addAttribute("book", book);
        return "edit";
    }

    @PostMapping("/book/{bookId}/edit")
    public String submitBookEdit(BookDetailsDto book, Model model) {
        Book savedBook = libraryService.updateBook(BookDetailsDto.toBook(book));
        model.addAttribute("book", savedBook);
        model.addAttribute("isSaved", Boolean.TRUE);
        return "edit";
    }
}
