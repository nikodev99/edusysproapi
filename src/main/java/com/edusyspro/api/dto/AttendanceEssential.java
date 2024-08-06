package com.edusyspro.api.dto;

import com.edusyspro.api.model.enums.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceEssential {
    private long id;
    private LocalDate attendanceDate;
    private AttendanceStatus status;
}
