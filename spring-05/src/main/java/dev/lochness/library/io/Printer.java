package dev.lochness.library.io;

import dev.lochness.library.domain.Author;
import dev.lochness.library.domain.Book;
import dev.lochness.library.domain.Genre;

public interface Printer {
    void printBookInfo(Book book);

    void printAuthor(Author author);

    void printGenre(Genre genre);
}
