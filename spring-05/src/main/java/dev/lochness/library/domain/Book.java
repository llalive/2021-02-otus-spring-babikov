package dev.lochness.library.domain;

import java.util.List;

public class Book {

    private final String title;
    private final String ISBN;
    private long id;
    private List<Author> authors;
    private List<Genre> genres;

    public Book(String title, String isbn) {
        this.title = title;
        this.ISBN = isbn;
    }

    public Book(long id, String title, String ISBN) {
        this.id = id;
        this.title = title;
        this.ISBN = ISBN;
    }

    public Book(long id, String title, String isbn, List<Author> authors, List<Genre> genres) {
        this.id = id;
        this.title = title;
        ISBN = isbn;
        this.setAuthors(authors);
        this.setGenres(genres);
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getISBN() {
        return ISBN;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", authors=" + getAuthors() +
                ", genres=" + getGenres() +
                '}';
    }
}
