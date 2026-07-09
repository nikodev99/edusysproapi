package com.edusyspro.api.dto;

import com.edusyspro.api.model.Planning;
import com.edusyspro.api.model.Semester;
import com.edusyspro.api.model.enums.PlanningStatus;
import com.edusyspro.api.utils.Datetime;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private ZonedDateTime termStartDate;
    private ZonedDateTime termEndDate;
    private GradeDTO grade;
    private PlanningStatus status;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public static PlanningDTO fromEntity(Planning dto) {
        return PlanningDTO.builder()
                .id(dto.getId())
                .designation(dto.getDesignation())
                .termStartDate(dto.getTermStartDate())
                .termEndDate(dto.getTermEndDate())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    public static Planning toEntity(PlanningDTO dto) {
        return Planning.builder()
                .id(dto.getId())
                .designation(dto.getDesignation())
                .termStartDate(dto.getTermStartDate())
                .termEndDate(dto.getTermEndDate())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    public static Planning fromAssignment(AssignmentDTO dto) {
        return Planning.builder()
                .designation(dto.getExamName())
                .termStartDate(Datetime.toZone(dto.getExamDate().atTime(dto.getStartTime())))
                .termEndDate(Datetime.toZone(dto.getExamDate().atTime(dto.getEndTime())))
                .createdAt(dto.getAddedDate() != null ? dto.getAddedDate() : Datetime.brazzavilleDatetime())
                .updatedAt(dto.getUpdatedDate() != null ? dto.getUpdatedDate() : Datetime.brazzavilleDatetime())
                .build();
    }

    public static Planning toEntityWithGrade(PlanningDTO dto) {
        Planning planning = toEntity(dto);
        planning.setGrade(dto.getGrade() != null ? dto.grade.toEntity() : null);
        return planning;
    }

    public Planning toMerge() {
        return Planning.builder().id(id).build();
    }
}
