package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.PlanningDTO;
import com.edusyspro.api.model.Semester;
import com.edusyspro.api.model.enums.PlanningStatus;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public record PlanningBasic(
       Long id,
       Integer semesterId,
       String designation,
       PlanningStatus status,
       ZonedDateTime termStartDate,
       ZonedDateTime termEndDate
) {
    public PlanningDTO toDto() {
        return PlanningDTO.builder()
                .id(id)
                .semestre(Semester.builder()
                        .semesterId(semesterId)
                        .build())
                .designation(designation)
                .status(status)
                .termStartDate(termStartDate)
                .termEndDate(termEndDate)
                .build();
    }
}
