package com.edusyspro.api.dto.custom;

import com.edusyspro.api.model.enums.AttendanceStatus;

public record IndividualAttendanceCount(AttendanceStatus status, Long count) {
}
