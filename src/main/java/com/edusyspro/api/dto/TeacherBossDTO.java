package com.edusyspro.api.dto;

import com.edusyspro.api.model.AcademicYear;
import com.edusyspro.api.model.ClasseTeacherBoss;
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

    public static ClasseTeacherBoss toEntity(TeacherBossDTO dto) {
        return ClasseTeacherBoss.builder()
                .id(dto.id)
                .academicYear(dto.getAcademicYear())
                .principalTeacher(TeacherDTO.toEntity(dto.getPrincipalTeacher()))
                .current(dto.isCurrent())
                .startPeriod(dto.getStartPeriod())
                .endPeriod(dto.getEndPeriod())
                .build();
    }
}
