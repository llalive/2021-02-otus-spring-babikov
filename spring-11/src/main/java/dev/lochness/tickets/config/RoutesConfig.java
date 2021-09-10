package dev.lochness.tickets.config;

import dev.lochness.tickets.handlers.ProgramHandler;
import dev.lochness.tickets.handlers.TicketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RoutesConfig {

    @Bean
    public RouterFunction<ServerResponse> programRoutes(ProgramHandler programHandler) {
        return route(GET("/api/program").and(accept(APPLICATION_JSON)), programHandler::list)
                .andRoute(POST("/api/program")
                        .and(accept(APPLICATION_JSON))
                        .and(contentType(APPLICATION_JSON)), programHandler::add);

    }

    @Bean
    public RouterFunction<ServerResponse> ticketRoutes(TicketHandler ticketHandler) {
        return route(GET("/api/ticket").and(accept(APPLICATION_JSON)), ticketHandler::list)
                .andRoute(DELETE("/api/ticket/{id}"), ticketHandler::delete);
    }

}
