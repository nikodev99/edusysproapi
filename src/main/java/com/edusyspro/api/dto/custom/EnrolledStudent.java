package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.ClasseDTO;
import com.edusyspro.api.dto.EnrollmentDTO;
import com.edusyspro.api.dto.StudentDTO;
import com.edusyspro.api.model.*;
import com.edusyspro.api.model.enums.Gender;
import com.edusyspro.api.model.enums.Section;
import lombok.Builder;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@Builder
public record EnrolledStudent(
        long enrollmentId,
        UUID id,
        Individual personalInfo,
        AcademicYear academicYear,
        String reference,
        ZonedDateTime lastEnrolledDate,
        int classeId,
        String classe,
        String classeCategory,
        Section grade,
        double monthCost,
        String dadName,
        String momName
) {

    public EnrollmentDTO populateStudent() {
        return EnrollmentDTO.builder()
                .id(enrollmentId())
                .academicYear(academicYear)
                .student(StudentDTO.builder()
                        .id(id)
                        .personalInfo(personalInfo)
                        .dadName(dadName)
                        .momName(momName)
                        .reference(reference)
                        .build())
                .enrollmentDate(lastEnrolledDate)
                .classe(ClasseDTO.builder()
                        .id(classeId)
                        .name(classe)
                        .category(classeCategory)
                        .grade(Grade.builder()
                                .section(grade)
                                .build())
                        .monthCost(monthCost)
                        .build())
                .build();
    }
}
