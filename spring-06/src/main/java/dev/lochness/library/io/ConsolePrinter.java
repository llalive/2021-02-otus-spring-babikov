package dev.lochness.library.io;

import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Book;
import dev.lochness.library.domain.Comment;
import dev.lochness.library.domain.Genre;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsolePrinter implements Printer {

    @Override
    public void printBookInfo(Book book, boolean withDetails) {
        System.out.printf("BOOK ID: %d, TITLE: %s, ISBN: %s\n",
                book.getId(), book.getTitle(), book.getIsbn());
        if (withDetails && !book.getAuthors().isEmpty()) {
            System.out.println("[BOOK AUTHORS]");
            for (Author author : book.getAuthors()) {
                printAuthor(author, false);
            }
        }
        if (withDetails && !book.getGenres().isEmpty()) {
            System.out.println("[BOOK GENRES]");
            for (Genre genre : book.getGenres()) {
                printGenre(genre, false);
            }
        }
        if (withDetails && !book.getComments().isEmpty()) {
            System.out.println("[BOOK COMMENTS]");
            for (Comment comment : book.getComments()) {
                printComment(comment);
            }
        }
    }

    @Override
    public void printAuthor(Author author, boolean withDetails) {
        System.out.printf("AUTHOR ID: %d, FIRSTNAME: %s, LASTNAME: %s\n",
                author.getId(), author.getFirstName(), author.getLastName());
        if (withDetails && !author.getBooks().isEmpty()) {
            System.out.println("[AUTHOR BOOKS]");
            for (Book book : author.getBooks()) {
                printBookInfo(book, false);
            }
        }
    }

    @Override
    public void printGenre(Genre genre, boolean withDetails) {
        System.out.printf("GENRE ID: %d, NAME: %s\n", genre.getId(), genre.getName());
        if (withDetails && !genre.getBooks().isEmpty()) {
            System.out.println("[BOOKS FOR THIS GENRE]");
            for (Book book : genre.getBooks()) {
                printBookInfo(book, false);
            }
        }
    }

    @Override
    public void printComment(Comment comment) {
        System.out.printf("COMMENT BY: %s, TEXT: %s\n", comment.getCommentedBy(), comment.getText());
    }
}
