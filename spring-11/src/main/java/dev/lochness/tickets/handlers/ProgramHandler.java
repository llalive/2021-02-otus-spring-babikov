package dev.lochness.tickets.handlers;

import dev.lochness.tickets.domain.Program;
import dev.lochness.tickets.exceptions.InvalidRequestException;
import dev.lochness.tickets.repository.ProgramRepository;
import dev.lochness.tickets.requests.ProgramAddRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
public class ProgramHandler {

    private final ProgramRepository programRepository;

    public ProgramHandler(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    public Mono<ServerResponse> add(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(ProgramAddRequest.class)
                .flatMap(this::addProgramIfRequestCorrect)
                .flatMap(program -> created(
                        UriComponentsBuilder.fromPath("/api/program/" + program.getId()).build().toUri())
                        .body(fromValue(program)))
                .onErrorResume(error -> badRequest()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(fromValue(error.getMessage())));
    }

    public Mono<ServerResponse> list(ServerRequest serverRequest) {
        return ok().contentType(MediaType.APPLICATION_JSON).body(fromPublisher(programRepository.findAll(),
                Program.class));
    }

    private Mono<Program> addProgramIfRequestCorrect(ProgramAddRequest programAddRequest) {
        return StringUtils.isNotBlank(programAddRequest.getName())
                ? programRepository.save(Program.from(programAddRequest))
                : Mono.error(new InvalidRequestException("Имя программы не задано"));
    }
}
