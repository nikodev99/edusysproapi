package com.edusyspro.api.dto.custom;

import com.edusyspro.api.model.enums.AttendanceStatus;

public record AttendanceStatusCount(AttendanceStatus status, long count) {
}
