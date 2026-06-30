package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.PlanningDTO;
import com.edusyspro.api.model.Semester;

import java.time.ZonedDateTime;

public record PlanningEssential(
        long id,
        String designation,
        ZonedDateTime termStartDate,
        ZonedDateTime termEndDate,
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
