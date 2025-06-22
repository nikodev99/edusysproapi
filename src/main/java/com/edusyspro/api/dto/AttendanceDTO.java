package com.edusyspro.api.dto;

import com.edusyspro.api.model.AcademicYear;
import com.edusyspro.api.model.Attendance;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.enums.AttendanceStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceDTO {
    private long id;
    private AcademicYear academicYear;
    private Individual individual;

    @JsonProperty("classe")
    private ClasseDTO classeEntity;

    private LocalDate attendanceDate;
    private AttendanceStatus status;

    public Attendance toEntity() {
        return Attendance.builder()
                .id(id)
                .academicYear(academicYear)
                .individual(individual)
                .classeEntity(classeEntity.toMergeEntity())
                .attendanceDate(attendanceDate)
                .status(status)
                .build();
    }
}
