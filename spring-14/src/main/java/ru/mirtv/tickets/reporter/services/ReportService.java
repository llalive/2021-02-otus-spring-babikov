package ru.mirtv.tickets.reporter.services;

import org.springframework.stereotype.Service;
import ru.mirtv.tickets.reporter.model.external.TicketsRecord;
import ru.mirtv.tickets.reporter.model.batch.ReportItem;

import java.time.Duration;
import java.time.format.DateTimeFormatter;

@Service
public class ReportService {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public ReportItem transform(TicketsRecord ticketsRecord) {
        var usageTime = Duration.ofMillis(ticketsRecord.getEndDate().getTime() -
                                          ticketsRecord.getStartDate().getTime());
        String usageTimeValue = usageTime.toHours() >= 1
                ? String.format("%d ч. %d мин.", usageTime.toHoursPart(), usageTime.toMinutesPart())
                : String.format("%d мин.", usageTime.toMinutes());
        var startDate = ticketsRecord.getStartDate().toLocalDateTime();
        return ReportItem.builder()
                .date(startDate.format(DATE_TIME_FORMATTER))
                .assemblyName(ticketsRecord.getObject())
                .program(ticketsRecord.getProgram())
                .usageTime(usageTimeValue)
                .projectCode(ticketsRecord.getCode())
                .build();
    }
}
