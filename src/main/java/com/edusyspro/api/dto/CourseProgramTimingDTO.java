package com.edusyspro.api.dto;

import com.edusyspro.api.model.enums.ProgramStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseProgramTimingDTO {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProgramStatus status;

    private ZonedDateTime completedAt;
    private ZonedDateTime updatedAt;
}
