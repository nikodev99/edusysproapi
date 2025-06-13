package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.PlanningDTO;
import com.edusyspro.api.model.Semester;

import java.time.LocalDate;

public record PlanningBasic(
       Long id,
       Integer semesterId,
       String designation,
       LocalDate termStartDate,
       LocalDate termEndDate
) {
    public PlanningDTO toDto() {
        return PlanningDTO.builder()
                .id(id)
                .semestre(Semester.builder()
                        .semesterId(semesterId)
                        .build())
                .designation(designation)
                .termStartDate(termStartDate)
                .termEndDate(termEndDate)
                .build();
    }
}
