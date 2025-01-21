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
public class TeacherBossDTO {
    private int id;
    private AcademicYear academicYear;
    private TeacherDTO principalTeacher;
    private boolean current;
    private LocalDate startPeriod;
    private LocalDate endPeriod;
}
