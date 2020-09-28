package dev.lochness.library.io;

import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Book;
import dev.lochness.library.domain.Comment;
import dev.lochness.library.domain.Genre;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ConsolePrinter implements Printer {

    @Override
    public void printBookInfo(Book book, boolean withDetails) {
        System.out.printf("BOOK ID: %s, TITLE: %s, ISBN: %s\n",
                book.getId(), book.getTitle(), book.getIsbn());
        if (withDetails && !book.getAuthors().isEmpty()) {
            System.out.println("[BOOK AUTHORS]");
            for (Author author : book.getAuthors()) {
                printAuthor(author);
            }
        }
        if (withDetails && !book.getGenres().isEmpty()) {
            System.out.println("[BOOK GENRES]");
            for (Genre genre : book.getGenres()) {
                printGenre(genre);
            }
        }
    }

    @Override
    public void printAuthor(Author author) {
        System.out.printf("AUTHOR ID: %s, FIRSTNAME: %s, LASTNAME: %s\n",
                author.getId(), author.getFirstName(), author.getLastName());
    }

    @Override
    public void printGenre(Genre genre) {
        System.out.printf("GENRE ID: %s, NAME: %s\n", genre.getId(), genre.getName());
    }


    private void printComment(Comment comment) {
        System.out.printf("COMMENT BY: %s, TEXT: %s\n", comment.getCommentedBy(), comment.getText());
    }

    @Override
    public void printComments(Collection<Comment> comments){
        if(!comments.isEmpty()) {
            System.out.println("[COMMENTS]");
            comments.stream().forEach(c -> printComment(c));
        }
    }
}
