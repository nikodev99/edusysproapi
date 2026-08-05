package com.edusyspro.api.dto;

import com.edusyspro.api.model.AcademicYear;
import com.edusyspro.api.model.ClasseStudentBoss;
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
public class StudentBossDTO {
    private Integer id;
    private AcademicYear academicYear;
    private ClasseDTO classe;
    private StudentDTO principalStudent;
    private Boolean current;
    private LocalDate startPeriod;
    private LocalDate endPeriod;

    public static StudentBossDTO fromEntity(ClasseStudentBoss dto) {
        return StudentBossDTO.builder()
                .id(dto.getId())
                .academicYear(dto.getAcademicYear())
                .classe(ClasseDTO.fromEntity(dto.getClasse()))
                .principalStudent(StudentDTO.fromEntity(dto.getPrincipalStudent()))
                .current(dto.getCurrent())
                .startPeriod(dto.getStartPeriod())
                .endPeriod(dto.getEndPeriod())
                .build();
    }

    public static ClasseStudentBoss toEntity(StudentBossDTO dto) {
        return ClasseStudentBoss.builder()
                .id(dto.getId())
                .academicYear(dto.getAcademicYear())
                .classe(dto.getClasse().toMergeEntity())
                .principalStudent(dto.getPrincipalStudent().toMergeEntity())
                .current(dto.getCurrent())
                .startPeriod(dto.getStartPeriod())
                .endPeriod(dto.getEndPeriod())
                .build();
    }
}
