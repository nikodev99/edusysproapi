package com.edusyspro.api.dto;

import com.edusyspro.api.model.*;
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
public class StudentDTO {
    private UUID id;
    private Individual personalInfo;

    @JsonProperty("enrollments")
    private List<EnrollmentEntity> enrollmentEntities;

    private String dadName;
    private String momName;
    private String reference;
    private GuardianEntity guardian;
    private HealthCondition healthCondition;
    private List<Score> marks;
    private List<Attendance> attendances;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;
}
