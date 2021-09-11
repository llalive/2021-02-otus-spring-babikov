package dev.lochness.library.controller;

import dev.lochness.library.domain.Book;
import dev.lochness.library.dto.BookDetailsDto;
import dev.lochness.library.exceptions.NotFoundException;
import dev.lochness.library.service.LibraryService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookController {

    private final LibraryService libraryService;
    private final MeterRegistry meterRegistry;

    public BookController(LibraryService libraryService,
                          MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.libraryService = libraryService;

        Counter.builder("books.views.count")
                .description("How many books viewed sinse app start")
                .register(meterRegistry);
    }

    @GetMapping("/")
    public String listBooks(@RequestParam(value = "offset", defaultValue = "0") int page, Model model) {
        model.addAttribute("books", libraryService.getBooksWithOffset(page));
        return "books";
    }

    @GetMapping("/book/{bookId}")
    public String getBookDetails(@PathVariable Long bookId, Model model) {
        BookDetailsDto book = libraryService.getBookById(bookId).orElseThrow(()
                -> new NotFoundException("Книга не найдена"));
        model.addAttribute("book", book);
        meterRegistry.counter("books.views.count").increment();
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
        Book savedBook = libraryService.updateBook(BookDetailsDto.toBook(book));
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
        libraryService.updateBook(BookDetailsDto.toBook(book));
        return "redirect:/";
    }

    @GetMapping("/book/{bookId}/delete")
    public String deleteBook(@PathVariable Long bookId) {
        libraryService.deleteBook(bookId);
        return "redirect:/";
    }
}
