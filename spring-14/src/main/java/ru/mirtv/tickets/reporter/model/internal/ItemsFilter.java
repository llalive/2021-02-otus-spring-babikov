package ru.mirtv.tickets.reporter.model.internal;

import java.util.Arrays;

public enum ItemsFilter {

    PREV_MONTH, ALL;

    public static ItemsFilter from(String filterName) {
        return Arrays.stream(ItemsFilter.values())
                .filter(filter -> filter.name().equals(filterName))
                .findFirst().orElse(null);
    }
}
