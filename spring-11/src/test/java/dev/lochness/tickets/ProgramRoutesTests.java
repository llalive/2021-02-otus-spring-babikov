package dev.lochness.tickets;

import dev.lochness.tickets.requests.ProgramAddRequest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.ValueMatcher;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@SpringBootTest
class ProgramRoutesTests {

    private static final String PROGRAM_NAME = "Program";
    private static final String PROGRAM_DESC = "Desc";
    private static final String EXPECTED_PROGRAM_ADD_RESPONSE = "{\"id\":\"x\",\"name\":\"Program\",\"desc\":\"Desc\"}";

    @Autowired
    private RouterFunction<ServerResponse> programRoutes;

    private WebTestClient client;

    @BeforeEach
    void init() {
        client = WebTestClient
                .bindToRouterFunction(programRoutes)
                .build();
    }

    @Test
    void shouldReturnListOfPrograms() {
        client.get().uri("/api/program")
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void shouldCorrectlyAddProgram() {
        client.post().uri("/api/program")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(buildProgramAddRequest())
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .consumeWith(response -> checkIfProgramAddResponseIsCorrect(response));
    }

    @Test
    void shouldReturnErrorIfNameNotSpecified() {
        client.post().uri("/api/program")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(buildInvalidAddRequest())
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @SneakyThrows
    private void checkIfProgramAddResponseIsCorrect(EntityExchangeResult<byte[]> response) {
        JSONAssert.assertEquals(EXPECTED_PROGRAM_ADD_RESPONSE,
                new String(response.getResponseBody()),
                new CustomComparator(JSONCompareMode.STRICT,
                        new Customization("id", new ValueMatcher<Object>() {
                            @Override
                            public boolean equal(Object o, Object t1) {
                                return true;
                            }
                        })));
    }

    private ProgramAddRequest buildProgramAddRequest() {
        return ProgramAddRequest.builder()
                .name(PROGRAM_NAME)
                .desc(PROGRAM_DESC)
                .build();
    }

    private ProgramAddRequest buildInvalidAddRequest() {
        return ProgramAddRequest.builder()
                .desc(PROGRAM_DESC)
                .build();
    }


}
