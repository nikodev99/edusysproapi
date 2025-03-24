package com.edusyspro.api.dto;

import com.edusyspro.api.model.AcademicYear;
import com.edusyspro.api.model.ClasseTeacherBoss;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeacherBossDTO {
    private Integer id;
    private AcademicYear academicYear;
    private TeacherDTO principalTeacher;
    private Boolean current;
    private LocalDate startPeriod;
    private LocalDate endPeriod;

    public static TeacherBossDTO fromEntity (ClasseTeacherBoss dto) {
        return TeacherBossDTO.builder()
                .id(dto.getId())
                .academicYear(dto.getAcademicYear())
                .principalTeacher(TeacherDTO.fromEntity(dto.getPrincipalTeacher()))
                .current(dto.isCurrent())
                .startPeriod(dto.getStartPeriod())
                .endPeriod(dto.getEndPeriod())
                .build();
    }

    public static ClasseTeacherBoss toEntity(TeacherBossDTO dto) {
        return ClasseTeacherBoss.builder()
                .id(dto.getId())
                .academicYear(dto.getAcademicYear())
                .principalTeacher(TeacherDTO.toEntity(dto.getPrincipalTeacher()))
                .current(dto.getCurrent())
                .startPeriod(dto.getStartPeriod())
                .endPeriod(dto.getEndPeriod())
                .build();
    }
}
