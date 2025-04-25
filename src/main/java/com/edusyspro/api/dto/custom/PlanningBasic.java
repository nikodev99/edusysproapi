package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.PlanningDTO;

import java.time.LocalDate;

public record PlanningBasic(
       long id,
       String designation,
       LocalDate termStartDate,
       LocalDate termEndDate
) {
    public PlanningDTO toDto() {
        return PlanningDTO.builder()
                .id(id)
                .designation(designation)
                .termStartDate(termStartDate)
                .termEndDate(termEndDate)
                .build();
    }
}
