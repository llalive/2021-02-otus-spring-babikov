package dev.lochness.library.shell;

import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Book;
import dev.lochness.library.domain.Comment;
import dev.lochness.library.domain.Genre;
import dev.lochness.library.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.Arrays;

@ShellComponent
public class LibraryInterfaceCommands {

    private final LibraryService service;

    @Autowired
    public LibraryInterfaceCommands(LibraryService bookService) {
        this.service = bookService;
    }

    @ShellMethod(value = "Print all books", key = {"list books", "lb"})
    public void listBooks() {
        service.listBooks();
    }

    @ShellMethod(value = "Print all authors", key = {"list authors", "la"})
    public void listAuthors() {
        service.listAuthors();
    }

    @ShellMethod(value = "Print all genres", key = {"list genres", "lg"})
    public void listGenres() {
        service.listGenres();
    }

    @ShellMethod(value = "Print book info", key = {"book", "b"})
    public void printBookInfo(@ShellOption String bookId) {
        service.printBookInfo(bookId);
    }

    @ShellMethod(value = "Print books by genre id", key = {"genre", "g"})
    public void printGenre(@ShellOption String genreId) {
        service.printBooksByGenre(genreId);
    }

    @ShellMethod(value = "Print books by author id", key = {"author", "a"})
    public void printAuthor(@ShellOption String authorId) {
        service.printBooksByAuthor(authorId);
    }

    @ShellMethod(value = "Add book to library", key = {"add book", "ab"})
    public void addBook(@ShellOption String title, @ShellOption(defaultValue = "-") String ISBN) {
        service.addBook(Book.builder()
                .title(title)
                .isbn(ISBN)
                .build());
    }

    @ShellMethod(value = "Add new genre", key = {"add genre", "ag"})
    public void addGenre(@ShellOption String name) {
        service.addGenre(Genre.builder()
                .name(name)
                .build());
    }

    @ShellMethod(value = "Add new author", key = {"add author", "aa"})
    public void addAuthor(@ShellOption String firstName, @ShellOption String lastName) {
        service.addAuthor(Author.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build());
    }

    @ShellMethod(value = "Delete book from library", key = {"delete book", "db"})
    public void deleteBook(@ShellOption String bookId) {
        service.deleteBook(bookId);
    }

    @ShellMethod(value = "Delete genre", key = {"delete genre", "dg"})
    public void deleteGenre(@ShellOption String genreId) {
        service.deleteGenre(genreId);
    }

    @ShellMethod(value = "Delete author", key = {"delete author", "da"})
    public void deleteAuthor(@ShellOption String authorId) {
        service.deleteAuthor(authorId);
    }

    @ShellMethod(value = "Set book authors", key = {"set book authors", "sba"})
    public void setBookAuthors(@ShellOption String bookId, @ShellOption String... authors) {
        service.setBookAuthors(bookId, Arrays.asList(authors));
    }

    @ShellMethod(value = "Set book genres", key = {"set book genres", "sbg"})
    public void setBookGenres(@ShellOption String bookId, @ShellOption String... genres) {
        service.setBookGenres(bookId, Arrays.asList(genres));
    }

    @ShellMethod(value = "Update book", key = {"update book", "ub"})
    public void updateBook(@ShellOption String bookId, @ShellOption String title,
                           @ShellOption String ISBN) {
        service.updateBook(bookId, title, ISBN);
    }

    @ShellMethod(value = "Add comment to book", key = {"add comment", "ac"})
    public void addComment(@ShellOption String bookId, @ShellOption(defaultValue = "Anonymous") String commentedBy,
                           @ShellOption String text) {
        service.addComment(bookId, Comment.builder()
                .commentedBy(commentedBy)
                .text(text)
                .build());
    }
}
