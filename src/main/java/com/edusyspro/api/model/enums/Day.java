package com.edusyspro.api.model.enums;

import java.time.DayOfWeek;
import java.time.LocalDate;

public enum Day {
    MONDAY(DayOfWeek.MONDAY),
    TUESDAY(DayOfWeek.TUESDAY),
    WEDNESDAY(DayOfWeek.WEDNESDAY),
    THURSDAY(DayOfWeek.THURSDAY),
    FRIDAY(DayOfWeek.FRIDAY),
    SATURDAY(DayOfWeek.SATURDAY),
    SUNDAY(DayOfWeek.SUNDAY),

    ALL_DAYS;

    private DayOfWeek dayOfWeek;

    Day() {
    }

    Day(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public DayOfWeek toDayOfWeek() {
        return dayOfWeek;
    }

    public static Day getCurrentDay() {
        DayOfWeek currentDayOfWeek = LocalDate.now().getDayOfWeek();
        for (Day day : values()) {
            if (day.toDayOfWeek() == currentDayOfWeek) {
                return day;
            }
        }
        throw new IllegalStateException("No matching day found for " + currentDayOfWeek);
    }
}
