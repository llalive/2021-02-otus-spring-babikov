package ru.mirtv.tickets.reporter.model.external;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketsRecord {
    private Timestamp startDate;
    private Timestamp endDate;
    private String program;
    private String code;
    private String object;
}
