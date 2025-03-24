package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.ExamDTO;
import com.edusyspro.api.model.ExamType;

import java.time.LocalDate;

public record ExamEssential(
        Long id,
        ExamType examType,
        LocalDate startDate,
        LocalDate endDate
) {
    public ExamDTO toDto() {
        return ExamDTO.builder()
                .id(id)
                .examType(examType)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
