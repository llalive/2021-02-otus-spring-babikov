package ru.mirtv.tickets.reporter.utils;

import lombok.experimental.UtilityClass;
import ru.mirtv.tickets.reporter.model.internal.ItemsFilter;
import ru.mirtv.tickets.reporter.model.internal.ReportType;

import java.time.LocalDate;

@UtilityClass
public class FilterUtils {

    public static final String EMPTY_STRING = "";

    public String getFilterByNameAndReportType(ItemsFilter itemsFilter, ReportType type) {
        String filter;
        if (ItemsFilter.PREV_MONTH.equals(itemsFilter)) {
            String startDate = getFirstDayOfPrevMonth().toString();
            String endDate = getLastDayOfPrevMonth().toString();
            switch (type) {
                case CARS:
                case SHOOTING_SETS:
                    filter = String.format("and ((s.send_time >= '%s' and s.send_time <= '%s') " +
                                           "or (s.complete_time >= '%s' and s.complete_time <= '%s'))",
                            startDate, startDate, endDate, endDate);
                    return filter;
                case ASSEMBLIES:
                    filter = String.format("and ((a.start_date >= '%s' and a.start_date <= '%s') " +
                                           "or (a.end_date >= '%s' and a.end_date <= '%s'))",
                            startDate, startDate, endDate, endDate);
                    return filter;
                default:
                    //do nothing
            }
        }
        return EMPTY_STRING;
    }

    private LocalDate getLastDayOfPrevMonth() {
        return getFirstDayOfPrevMonth().plusMonths(1).minusDays(1);
    }

    private LocalDate getFirstDayOfPrevMonth() {
        return LocalDate.now().minusMonths(1).withDayOfMonth(1);
    }
}
