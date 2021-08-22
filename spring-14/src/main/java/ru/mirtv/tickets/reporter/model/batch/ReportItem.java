package ru.mirtv.tickets.reporter.model.batch;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ReportItem {
    private String date;
    private String program;
    private String projectCode;
    private String usageTime;
    private String assemblyName;
}
