package dev.lochness.library.repository;

import dev.lochness.library.domain.Book;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"dev.lochness.library.config", "dev.lochness.library.repositories"})
@DisplayName("BookRepository")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    private Book firstBook;

    @BeforeEach
    void setFirstBook() {
        firstBook = bookRepository.findAll().get(0);
    }

    @Test
    void shouldReturnCorrectAuthorsList() {
        val authors = bookRepository.getBookAuthorsById(firstBook.getId());
        assertThat(firstBook.getAuthors()).isNotNull().containsAll(authors);
    }

    @Test
    void shouldReturnCorrectGenresList() {
        val genres = bookRepository.getBookGenresById(firstBook.getId());
        assertThat(firstBook.getGenres()).isNotNull().containsAll(genres);
    }
}