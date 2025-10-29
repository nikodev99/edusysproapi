package com.edusyspro.api.dto;

import com.edusyspro.api.model.AcademicYear;
import com.edusyspro.api.model.School;
import com.edusyspro.api.model.Semester;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
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
    private List<Semester> semesters;
    private School school;

    public static AcademicYearDTO toDto(AcademicYear academicYear) {
        return AcademicYearDTO.builder()
                .id(academicYear.getId())
                .startDate(academicYear.getStartDate())
                .endDate(academicYear.getEndDate())
                .current(academicYear.getCurrent())
                .years(academicYear.getYears())
                //.semesters(academicYear.getSemesters() != null ? academicYear.getSemesters() : List.of())
                //.school(academicYear.getSchool() != null ? academicYear.getSchool() : School.builder().build())
                .build();
    }

    public AcademicYear toEntity() {
        return AcademicYear.builder()
                .id(id)
                .startDate(startDate)
                .endDate(endDate)
                .current(current)
                .years(years)
                .semesters(semesters)
                .school(school)
                .build();
    }
}
