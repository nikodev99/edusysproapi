package com.edusyspro.api.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class Essaie {

    public static void main(String[] args) {
        var date = "2025-06-09";

        System.out.println("DATE: " + ZonedDateTime.parse(date).toLocalDate());
    }

}
