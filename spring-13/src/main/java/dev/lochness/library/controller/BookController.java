package dev.lochness.library.controller;

import dev.lochness.library.domain.Book;
import dev.lochness.library.dto.BookDetailsDto;
import dev.lochness.library.exceptions.NotFoundException;
import dev.lochness.library.service.LibraryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class BookController {

    private final LibraryService libraryService;

    @GetMapping("/")
    public String listBooks(Model model) {
        model.addAttribute("books", libraryService.getBooks());
        return "books";
    }

    @GetMapping("/book/{bookId}")
    public String getBookDetails(@PathVariable Long bookId, Model model) {
        BookDetailsDto book = libraryService.getBookById(bookId).orElseThrow(()
                -> new NotFoundException("Книга не найдена"));
        model.addAttribute("book", book);
        return "book_details";
    }

    @GetMapping("/book/{bookId}/edit")
    public String getBookEditPage(@PathVariable Long bookId, Model model) {
        BookDetailsDto book = libraryService.getBookById(bookId).orElseThrow(()
                -> new NotFoundException("Книга не найдена"));
        model.addAttribute("book", book);
        return "book_edit";
    }

    @PostMapping("/book/{bookId}/edit")
    public String submitBookEdit(@PathVariable Long bookId, BookDetailsDto book, Model model) {
        book.setId(bookId);
        Book savedBook = libraryService.updateOrCreateBook(BookDetailsDto.toBook(book));
        model.addAttribute("book", BookDetailsDto.toDto(savedBook));
        model.addAttribute("isSaved", Boolean.TRUE);
        return "book_edit";
    }

    @GetMapping("/book/add")
    public String getBookAddPage() {
        return "book_add";
    }

    @PostMapping("/book/add")
    public String submitBook(BookDetailsDto book) {
        libraryService.updateOrCreateBook(BookDetailsDto.toBook(book));
        return "redirect:/";
    }

    @GetMapping("/book/{bookId}/delete")
    public String deleteBook(@PathVariable Long bookId) {
        libraryService.deleteBook(bookId);
        return "redirect:/";
    }
}
