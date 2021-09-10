package dev.lochness.tickets.repository;

import dev.lochness.tickets.domain.Program;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProgramRepository extends ReactiveMongoRepository<Program, String> {

}
