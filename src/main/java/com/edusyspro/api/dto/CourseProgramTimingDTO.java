package com.edusyspro.api.dto;

import com.edusyspro.api.model.CourseProgramTiming;
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
    private DateRange dateRange;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProgramStatus status;

    private ZonedDateTime completedAt;
    private ZonedDateTime updatedAt;

    private AcademicYearDTO academicYear;

    public CourseProgramTiming toTiming() {
        return CourseProgramTiming.builder()
                .id(id)
                .startDate(dateRange != null ? dateRange.startDate() : startDate)
                .endDate(dateRange != null ? dateRange.endDate() : endDate)
                .status(status)
                .completedAt(completedAt)
                .updatedAt(updatedAt)
                .academicYear(academicYear != null ? academicYear.toMergeEntity() : null)
                .build();
    }
}
