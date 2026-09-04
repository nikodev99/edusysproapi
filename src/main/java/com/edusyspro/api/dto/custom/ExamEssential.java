package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.AcademicYearDTO;
import com.edusyspro.api.dto.ExamDTO;
import com.edusyspro.api.model.ExamType;

import java.time.LocalDate;
import java.util.UUID;

public record ExamEssential(
        Integer id,
        ExamType examType,
        LocalDate startDate,
        LocalDate endDate,
        UUID academicYearId,
        String academicYear,
        Boolean isCurrent
) {
    public ExamDTO toDto() {
        return ExamDTO.builder()
                .id(id)
                .examType(examType)
                .startDate(startDate)
                .endDate(endDate)
                .academicYear(AcademicYearDTO.builder()
                        .id(academicYearId)
                        .years(academicYear)
                        .current(isCurrent)
                        .build())
                .build();
    }
}
