package dev.lochness.tickets.domain;

import dev.lochness.tickets.requests.ProgramAddRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Program {

    private String id;
    private String name;
    private String desc;

    public static Program from(ProgramAddRequest programAddRequest) {
        return Program.builder()
                .desc(programAddRequest.getDesc())
                .name(programAddRequest.getName())
                .build();
    }
}
