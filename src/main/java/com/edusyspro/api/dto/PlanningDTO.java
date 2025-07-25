package com.edusyspro.api.dto;

import com.edusyspro.api.model.Planning;
import com.edusyspro.api.model.Semester;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class PlanningDTO {
    private Long id;
    @JsonProperty("semester")
    private Semester semestre;
    private String designation;
    private LocalDate termStartDate;
    private LocalDate termEndDate;
    private GradeDTO grade;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public static PlanningDTO fromEntity(Planning dto) {
        return PlanningDTO.builder()
                .id(dto.getId())
                .semestre(dto.getSemestre())
                .designation(dto.getDesignation())
                .termStartDate(dto.getTermStartDate())
                .termEndDate(dto.getTermEndDate())
                .grade(GradeDTO.fromEntity(dto.getGrade()))
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    public static Planning toEntity(PlanningDTO dto) {
        return Planning.builder()
                .id(dto.getId())
                .semestre(dto.getSemestre())
                .designation(dto.getDesignation())
                .termStartDate(dto.getTermStartDate())
                .termEndDate(dto.getTermEndDate())
                .grade(dto.getGrade().toEntity())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
}
