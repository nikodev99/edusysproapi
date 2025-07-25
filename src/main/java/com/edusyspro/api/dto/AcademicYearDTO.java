package com.edusyspro.api.dto;

import com.edusyspro.api.model.AcademicYear;
import com.edusyspro.api.model.School;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcademicYearDTO {
    private UUID id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean current;
    @JsonProperty("academicYear")
    private String years;
    private School school;

    public AcademicYear toEntity() {
        return AcademicYear.builder()
                .id(id)
                .startDate(startDate)
                .endDate(endDate)
                .current(current)
                .years(years)
                .school(school)
                .build();
    }
}
