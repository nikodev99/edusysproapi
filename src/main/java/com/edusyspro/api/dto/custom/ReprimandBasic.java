package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.*;
import com.edusyspro.api.model.enums.ReprimandType;

import java.time.ZonedDateTime;
import java.util.UUID;

public record ReprimandBasic(
        Long id,
        UUID academicYearId,
        String academicYear,
        UUID studentId,
        Integer classId,
        String studentClasse,
        ZonedDateTime reprimandDate,
        ReprimandType type
) {
    public ReprimandDTO toDto() {
        return ReprimandDTO.builder()
                .id(id)
                .student(EnrollmentDTO.builder()
                        .student(StudentDTO.builder()
                                .id(studentId)
                                .build())
                        .academicYear(AcademicYearDTO.builder()
                                .id(academicYearId)
                                .years(academicYear)
                                .build())
                        .classe(ClasseDTO.builder()
                                .id(classId)
                                .name(studentClasse)
                                .build())
                        .build())
                .reprimandDate(reprimandDate)
                .type(type)
                .build();
    }
}
