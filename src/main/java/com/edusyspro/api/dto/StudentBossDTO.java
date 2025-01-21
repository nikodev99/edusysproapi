package com.edusyspro.api.dto;

import com.edusyspro.api.model.AcademicYear;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentBossDTO {
    private int id;
    private AcademicYear academicYear;
    private StudentDTO principalStudent;
    private boolean current;
    private LocalDate startPeriod;
    private LocalDate endPeriod;
}
