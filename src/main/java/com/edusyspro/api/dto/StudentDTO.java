package com.edusyspro.api.dto;

import com.edusyspro.api.model.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDTO {
    private UUID id;
    private Individual personalInfo;
    @JsonProperty("enrollments")
    private List<EnrollmentDTO> enrollmentEntities;
    private String dadName;
    private String momName;
    private String reference;
    private GuardianEntity guardian;
    private HealthCondition healthCondition;
    private List<ScoreDTO> marks;
    private List<AttendanceDTO> attendances;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;

    public static StudentDTO fromEntity(StudentEntity student) {
        return StudentDTO.builder()
                .id(student.getId())
                .personalInfo(student.getPersonalInfo())
                .enrollmentEntities(student.getEnrollmentEntities().stream().map(EnrollmentDTO::fromEntity).toList())
                .dadName(student.getDadName())
                .momName(student.getMomName())
                .reference(student.getReference())
                .guardian(student.getGuardian())
                .healthCondition(student.getHealthCondition())
                .build();
    }

    public static StudentEntity toEntity(StudentDTO student) {
        return StudentEntity.builder()
                .id(student.getId())
                .personalInfo(student.getPersonalInfo())
                .dadName(student.getDadName())
                .momName(student.getMomName())
                .reference(student.getReference())
                .guardian(student.getGuardian())
                .healthCondition(student.getHealthCondition())
                .build();
    }
}
