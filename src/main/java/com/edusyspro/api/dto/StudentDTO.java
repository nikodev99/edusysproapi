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
    private GuardianDTO guardian;
    private HealthCondition healthCondition;
    private List<ScoreDTO> marks;
    private List<AttendanceDTO> attendances;
    private School school;
    private ClasseDTO classe;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;

    public static StudentDTO fromEntity(StudentEntity student) {
        return StudentDTO.builder()
                .id(student.getId())
                .personalInfo(student.getPersonalInfo())
                .dadName(student.getDadName())
                .momName(student.getMomName())
                .guardian(GuardianDTO.fromEntity(student.getGuardian()))
                .healthCondition(student.getHealthCondition())
                .build();
    }

    public static StudentEntity toEntity(StudentDTO student) {
        return StudentEntity.builder()
                .id(student.getId())
                .personalInfo(student.getPersonalInfo())
                .dadName(student.getDadName())
                .momName(student.getMomName())
                .guardian(GuardianEntity.builder()
                        .id(student.getGuardian().getId())
                        .personalInfo(student.getGuardian().getPersonalInfo())
                        .jobTitle(student.getGuardian().getJobTitle())
                        .company(student.getGuardian().getCompany())
                        .linkToStudent(student.getGuardian().getLinkToStudent())
                        .createdAt(student.getGuardian().getCreatedAt())
                        .modifyAt(student.getGuardian().getModifyAt())
                        .build())
                .healthCondition(student.getHealthCondition())
                .build();
    }

    public static StudentEntity toMergeEntity(StudentDTO student) {
        return StudentEntity.builder()
                .id(student.getId())
                .build();
    }
}
