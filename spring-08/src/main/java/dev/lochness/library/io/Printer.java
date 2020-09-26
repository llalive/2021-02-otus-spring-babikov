package dev.lochness.library.io;

import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Book;
import dev.lochness.library.domain.Comment;
import dev.lochness.library.domain.Genre;

import java.util.Collection;

public interface Printer {

    void printBookInfo(Book book, boolean printDetails);

    void printAuthor(Author author);

    void printGenre(Genre genre);

    void printComments(Collection<Comment> comment);
}
