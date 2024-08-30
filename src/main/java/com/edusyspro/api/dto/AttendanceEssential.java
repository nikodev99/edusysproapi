package com.edusyspro.api.dto;

import com.edusyspro.api.model.Attendance;
import com.edusyspro.api.model.enums.AttendanceStatus;

import java.time.LocalDate;

public record AttendanceEssential (long id, LocalDate attendanceDate, AttendanceStatus attendanceStatus) {

    public static Attendance populate(AttendanceEssential att) {
        return Attendance.builder()
                    .id(att.id)
                    .attendanceDate(att.attendanceDate)
                    .status(att.attendanceStatus)
                .build();
    }

}
