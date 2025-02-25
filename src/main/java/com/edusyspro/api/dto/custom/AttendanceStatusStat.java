package com.edusyspro.api.dto.custom;

public record AttendanceStatusStat(
        String date,
        byte present,
        byte absent,
        byte late,
        byte excused
) {
}
