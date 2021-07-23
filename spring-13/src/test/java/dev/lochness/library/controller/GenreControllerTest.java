package dev.lochness.library.controller;

import dev.lochness.library.ControllerTest;
import dev.lochness.library.domain.Genre;
import dev.lochness.library.service.LibraryService;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class GenreControllerTest extends ControllerTest {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private LibraryService libraryService;

    @Autowired
    private MockMvc mvc;

    @WithMockUser(
            username = "user",
            authorities = {"USER"}
    )
    @Test
    void shouldReturnAllGenres() throws Exception {
        given(libraryService.getGenres()).willReturn(List.of(Genre.builder()
                        .id(1L)
                        .name("Fantasy")
                        .build(),
                Genre.builder()
                        .id(2L)
                        .name("Poem")
                        .build()));
        this.mvc.perform(MockMvcRequestBuilders.get("/api/genres")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(new BaseMatcher<>() {
                    @Override
                    public boolean matches(Object o) {
                        String s = (String) o;
                        return s.contains("Fantasy") && s.contains("Poem");
                    }

                    @Override
                    public void describeTo(Description description) {
                    }
                }));
    }

    @Test
    @WithAnonymousUser
    void shouldReturnGenresToAnonymous() throws Exception {
        this.mvc.perform(MockMvcRequestBuilders.get("/api/genres"))
                .andExpect(status().isOk());
    }
}