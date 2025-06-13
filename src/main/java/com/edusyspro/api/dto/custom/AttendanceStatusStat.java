package com.edusyspro.api.dto.custom;

public record AttendanceStatusStat(
        String date,
        Long present,
        Long absent,
        Long late,
        Long excused
) {
}
