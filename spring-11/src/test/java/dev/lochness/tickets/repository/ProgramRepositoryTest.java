package dev.lochness.tickets.repository;

import dev.lochness.tickets.domain.Program;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataMongoTest
class ProgramRepositoryTest {

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private ReactiveMongoTemplate testMongoTemplate;

    @Test
    void shouldCorrectlySaveProgram() {
        testMongoTemplate.dropCollection(Program.class);

        String name = "Program";
        String desc = "Description";
        Mono<Program> program = programRepository.save(Program.builder()
                .name(name)
                .desc(desc)
                .build());

        StepVerifier.create(program)
                .assertNext(savedProgram -> Assertions.assertThat(savedProgram)
                        .matches(p -> p.getId() != null && p.getName().equals(name) && p.getDesc().equals(desc)));
    }
}