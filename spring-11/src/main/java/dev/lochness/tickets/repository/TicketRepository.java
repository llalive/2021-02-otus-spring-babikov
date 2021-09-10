package dev.lochness.tickets.repository;

import dev.lochness.tickets.domain.Ticket;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface TicketRepository extends ReactiveMongoRepository<Ticket, String> {

    Flux<Ticket> findAllByProgramName(String programName);
}
