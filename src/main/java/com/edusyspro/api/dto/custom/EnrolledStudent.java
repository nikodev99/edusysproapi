package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.*;
import com.edusyspro.api.model.*;
import com.edusyspro.api.model.enums.Section;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Builder
public record EnrolledStudent(
        long enrollmentId,
        UUID id,
        Individual personalInfo,
        AcademicYear academicYear,
        ZonedDateTime lastEnrolledDate,
        int classeId,
        String classe,
        String classeCategory,
        Section grade,
        Boolean isArchived,
        BigDecimal monthCost,
        String dadName,
        String momName
) {

    public EnrollmentDTO populateStudent() {
        return EnrollmentDTO.builder()
                .id(enrollmentId())
                .academicYear(AcademicYearDTO.builder()
                        .id(academicYear.getId())
                        .startDate(academicYear.getStartDate())
                        .endDate(academicYear.getEndDate())
                        .current(academicYear.getCurrent())
                        .build())
                .student(StudentDTO.builder()
                        .id(id)
                        .personalInfo(personalInfo)
                        .dadName(dadName)
                        .momName(momName)
                        .build())
                .enrollmentDate(lastEnrolledDate)
                .classe(ClasseDTO.builder()
                        .id(classeId)
                        .name(classe)
                        .category(classeCategory)
                        .grade(GradeDTO.builder()
                                .section(grade)
                                .build())
                        .monthCost(monthCost)
                        .build())
                .isArchived(isArchived)
                .build();
    }
}
