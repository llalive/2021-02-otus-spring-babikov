package dev.lochness.tickets.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Ticket {

    private String id;

    private String title;
    private Program program;
    private Integer createdBy;
    private String address;
    private String status;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private LocalDateTime sendTime;
    private LocalDateTime startTime;
    private LocalDateTime returnTime;
    private LocalDateTime completeTime;
}
