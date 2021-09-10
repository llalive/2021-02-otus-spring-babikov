package dev.lochness.tickets.handlers;

import dev.lochness.tickets.domain.Ticket;
import dev.lochness.tickets.repository.TicketRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
public class TicketHandler {

    private final TicketRepository ticketRepository;

    public TicketHandler(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        return ticketRepository.findById(serverRequest.pathVariable("id"))
                .flatMap(ticket -> noContent().build(ticketRepository.delete(ticket)))
                .switchIfEmpty(notFound().build());

    }

    public Mono<ServerResponse> list(ServerRequest serverRequest) {
        return ok().contentType(MediaType.APPLICATION_JSON)
                .body(fromPublisher(ticketRepository.findAll(), Ticket.class));
    }
}
