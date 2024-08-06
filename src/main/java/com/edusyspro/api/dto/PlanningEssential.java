package com.edusyspro.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanningEssential {
    private long id;
    private String designation;
    private LocalDate termStartDate;
    private LocalDate termEndDate;
    private String semestre;
}
