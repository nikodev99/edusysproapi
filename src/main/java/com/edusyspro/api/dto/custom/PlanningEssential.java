package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.PlanningDTO;
import com.edusyspro.api.model.AcademicYear;
import com.edusyspro.api.model.Semester;

import java.time.LocalDate;
import java.util.UUID;

public record PlanningEssential(
        long id,
        String designation,
        LocalDate termStartDate,
        LocalDate termEndDate,
        Semester semestre
) {
    public PlanningDTO toDto() {
        return PlanningDTO.builder()
                .id(id)
                .designation(designation)
                .termStartDate(termStartDate)
                .termEndDate(termEndDate)
                .semestre(semestre)
                .build();
    }
}
