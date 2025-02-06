package com.edusyspro.api.dto;

import com.edusyspro.api.model.AcademicYear;
import com.edusyspro.api.model.EnrollmentEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnrollmentDTO {
    private Long id;
    private AcademicYear academicYear;
    @JsonProperty("student")
    private StudentDTO student;
    @JsonProperty("classe")
    private ClasseDTO classe;
    private ZonedDateTime enrollmentDate;
    private Boolean isArchived;

    public static EnrollmentDTO fromEntity(EnrollmentEntity dto) {
        return EnrollmentDTO.builder()
                .id(dto.getId())
                .academicYear(dto.getAcademicYear())
                .student(StudentDTO.fromEntity(dto.getStudent()))
                .classe(ClasseDTO.fromEntity(dto.getClasse()))
                .enrollmentDate(dto.getEnrollmentDate())
                .isArchived(dto.isArchived())
                .build();
    }

    public static EnrollmentEntity toEntity(EnrollmentDTO dto) {
        return EnrollmentEntity.builder()
                .id(dto.getId())
                .academicYear(dto.getAcademicYear())
                .student(StudentDTO.toEntity(dto.getStudent()))
                .classe(ClasseDTO.toEntity(dto.getClasse()))
                .enrollmentDate(dto.getEnrollmentDate())
                .isArchived(dto.getIsArchived())
                .build();
    }
}
