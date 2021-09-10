package dev.lochness.tickets.repository;

import dev.lochness.tickets.domain.Program;
import dev.lochness.tickets.domain.Ticket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@DataMongoTest
class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ReactiveMongoTemplate testMongoTemplate;

    @Test
    void shouldReturnTicketIfSearchedByProgram() {
        testMongoTemplate.dropCollection(Ticket.class);

        String name = "Program";
        Ticket ticket = ticketRepository.save(
                Ticket.builder()
                        .program(Program.builder()
                                .name(name)
                                .build())
                        .build()).block();

        Flux<Ticket> allByProgramName = ticketRepository.findAllByProgramName(name);
        StepVerifier.create(allByProgramName)
                .expectNext(ticket)
                .verifyComplete();
    }
}