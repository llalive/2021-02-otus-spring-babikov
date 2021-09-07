package dev.lochness.tickets;

import dev.lochness.tickets.domain.Ticket;
import dev.lochness.tickets.repository.TicketRepository;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@SpringBootTest
class TicketRoutesTests {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private RouterFunction<ServerResponse> ticketRoutes;

    private WebTestClient client;

    private static final String TITLE = "Title";
    private static final String EXPECTED_TICKET_ADD_RESPONSE = "[{\"title\":\"Title\"}]";

    @BeforeEach
    void init() {
        client = WebTestClient
                .bindToRouterFunction(ticketRoutes)
                .build();
    }

    @Test
    void shouldReturnListOfPrograms() {
        createTicket();
        client.get().uri("/api/ticket")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(this::checkIfResponseHasTickets);
    }

    @Test
    void shouldCorrectlyDeleteTicket() {
        Ticket ticket = createTicket();
        Ticket byId = ticketRepository.findById(ticket.getId()).block();
        client.delete().uri("/api/ticket/" + ticket.getId())
                .exchange()
                .expectStatus()
                .isNoContent();
    }

    @Test
    void shouldThrowNotFoundOnTicketDeleteIfNotExists() {
        client.delete().uri("/api/ticket/1")
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    private Ticket createTicket() {
        return ticketRepository.save(Ticket.builder()
                .title(TITLE)
                .build()).block();
    }

    @SneakyThrows
    private void checkIfResponseHasTickets(EntityExchangeResult<byte[]> response) {
        Assertions.assertThat(new JSONArray(new String(response.getResponseBody())).length())
                .isPositive();
    }

}
