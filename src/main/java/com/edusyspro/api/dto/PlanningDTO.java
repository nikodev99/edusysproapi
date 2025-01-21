package com.edusyspro.api.dto;

import com.edusyspro.api.model.Semester;
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
public class PlanningDTO {
    private long id;
    private Semester semestre;
    private String designation;
    private LocalDate termStartDate;
    private LocalDate termEndDate;
    private GradeDTO grade;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
