package dev.lochness.library.controller;

import dev.lochness.library.dto.BookDetailsDto;
import dev.lochness.library.exceptions.NotFoundException;
import dev.lochness.library.service.LibraryService;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    private static final Long FIRST_BOOK_ID = 1L;
    private static final String FIRST_BOOK_TITLE = "Я и Арнольд";
    private static final String FIRST_BOOK_AUTHORS = "Александр Курицын, Арнольд Шварцнеггер";
    private static final String FIRST_BOOK_GENRES = "драма, роман";
    private static final String FIRST_BOOK_ISBN = "01234-5678-9";
    @Autowired
    private MockMvc mvc;
    @MockBean
    private LibraryService libraryService;

    @Test
    public void shouldRenderBookDetailsCorrectly() throws Exception {
        given(libraryService.getBookById(FIRST_BOOK_ID)).willReturn(Optional.of(getBookDetails()));
        this.mvc.perform(get("/book/1")).andExpect(content().string(new BookDetailsMatcher()));
    }

    @Test
    public void shouldReturn404IfBookNotFound() throws Exception {
        given(libraryService.getBookById(FIRST_BOOK_ID)).willReturn(Optional.empty());
        this.mvc.perform(get("/book/1"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
    }

    private BookDetailsDto getBookDetails() {
        return BookDetailsDto
                .builder()
                .id(FIRST_BOOK_ID)
                .title(FIRST_BOOK_TITLE)
                .authors(FIRST_BOOK_AUTHORS)
                .genres(FIRST_BOOK_GENRES)
                .isbn(FIRST_BOOK_ISBN)
                .comments(List.of())
                .build();
    }

    private class BookDetailsMatcher extends BaseMatcher<String> {
        @Override
        public void describeTo(Description description) {
        }

        @Override
        public boolean matches(Object o) {
            String output = (String) o;
            return output.contains(FIRST_BOOK_AUTHORS)
                    && output.contains(String.valueOf(FIRST_BOOK_ID))
                    && output.contains(FIRST_BOOK_GENRES)
                    && output.contains(FIRST_BOOK_TITLE)
                    && output.contains(FIRST_BOOK_ISBN);
        }
    }

}