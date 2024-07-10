package com.edusyspro.api.entities.dtos;

import com.edusyspro.api.entities.enums.AttendanceStatus;
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
