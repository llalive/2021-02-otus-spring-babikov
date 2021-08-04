package dev.lochness.library.controller;

import dev.lochness.library.ControllerTest;
import dev.lochness.library.dto.BookDetailsDto;
import dev.lochness.library.exceptions.NotFoundException;
import dev.lochness.library.service.LibraryService;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BookControllerTest extends ControllerTest {

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
    @WithMockUser(
            username = "user",
            authorities = {"USER"}
    )
    void shouldRenderBookDetailsCorrectly() throws Exception {
        given(libraryService.getBookById(FIRST_BOOK_ID))
                .willReturn(Optional.of(getBookDetails()));
        this.mvc.perform(get("/book/1"))
                .andExpect(content().string(new BookDetailsMatcher()));
    }

    @Test
    @WithMockUser(
            username = "user",
            authorities = {"USER"}
    )
    void shouldReturn404IfBookNotFound() throws Exception {
        given(libraryService.getBookById(FIRST_BOOK_ID)).willReturn(Optional.empty());
        this.mvc.perform(get("/book/1"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
    }

    @Test
    @WithAnonymousUser
    void shouldAllowAnonymousToReadIndex() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"ADMIN"}
    )
    void shouldAllowAccessToBookAddPageForAdmin() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/book/add"))
                .andExpect(status().isOk());
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