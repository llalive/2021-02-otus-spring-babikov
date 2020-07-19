package dev.lochness.library.io;

import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Book;
import dev.lochness.library.domain.Genre;
import org.springframework.stereotype.Service;

@Service
public class ConsolePrinter implements Printer {
    @Override
    public void printBookInfo(Book book) {
        System.out.printf("BOOK ID: %d, TITLE: %s, ISBN: %s\n",
                book.getId(), book.getTitle(), book.getISBN());
        if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
            System.out.println("[BOOK AUTHORS]");
            for (Author author : book.getAuthors()) {
                printAuthor(author);
            }
        }
        if (book.getGenres() != null && !book.getGenres().isEmpty()) {
            System.out.println("[BOOK GENRES]");
            for (Genre genre : book.getGenres()) {
                printGenre(genre);
            }
        }
    }

    @Override
    public void printAuthor(Author author) {
        System.out.printf("AUTHOR ID: %d, FIRSTNAME: %s, LASTNAME: %s\n",
                author.getId(), author.getFirstName(), author.getLastName());
        if (author.getBooks() != null && !author.getBooks().isEmpty()) {
            System.out.println("[AUTHOR BOOKS]");
            for (Book book : author.getBooks()) {
                printBookInfo(book);
            }
        }
    }

    @Override
    public void printGenre(Genre genre) {
        System.out.printf("GENRE ID: %d, NAME: %s\n", genre.getId(), genre.getName());
        if (genre.getBooks() != null && !genre.getBooks().isEmpty()) {
            System.out.println("[BOOKS FOR THIS GENRE]");
            for (Book book : genre.getBooks()) {
                printBookInfo(book);
            }
        }
    }
}
