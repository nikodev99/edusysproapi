package com.edusyspro.api.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class CardExpiryValidator implements ConstraintValidator<CardExpiry, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) return true; // presence with @NotBlank
        String v = value.trim();
        try {
            // Accept MM/yy or MM/yyyy
            if (v.matches("^\\d{2}/\\d{2}$")) {
                DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/yy");
                int month = Integer.parseInt(v.substring(0,2));
                int year = Integer.parseInt(v.substring(3,5)) + 2000;
                return monthValidAndNotPast(month, year);
            } else if (v.matches("^\\d{2}/\\d{4}$")) {
                int month = Integer.parseInt(v.substring(0,2));
                int year = Integer.parseInt(v.substring(3,7));
                return monthValidAndNotPast(month, year);
            } else {
                return false;
            }
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private boolean monthValidAndNotPast(int month, int year) {
        if (month < 1 || month > 12) return false;
        LocalDate lastDayOfMonth = LocalDate.of(year, month, 1).with(TemporalAdjusters.lastDayOfMonth());
        return !lastDayOfMonth.isBefore(LocalDate.now());
    }
}
