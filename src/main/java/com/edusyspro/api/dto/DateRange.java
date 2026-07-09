package com.edusyspro.api.dto;

import java.time.LocalDate;

public record DateRange(LocalDate startDate, LocalDate endDate) {
    public boolean contains(LocalDate date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
}
