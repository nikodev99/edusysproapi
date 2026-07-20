package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.*;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.School;
import com.edusyspro.api.model.enums.Section;

import java.util.UUID;

public record EnrolledStudentBasic (
    long enrollmentId,
    UUID id,
    UUID academicYearId,
    String academicYear,
    Integer classeId,
    String classeName,
    Section classeSection,
    long individualId,
    String firstName,
    String lastName,
    String image,
    String reference,
    Boolean isArchived,
    String schoolName
) {
    public EnrollmentDTO toDTO() {
        return EnrollmentDTO.builder()
                .id(enrollmentId)
                .academicYear(AcademicYearDTO.builder()
                        .id(academicYearId)
                        .years(academicYear)
                        .school(School.builder()
                                .name(schoolName)
                                .build())
                        .build())
                .student(StudentDTO.builder()
                        .id(id)
                        .personalInfo(Individual.builder()
                                .id(individualId)
                                .firstName(firstName)
                                .lastName(lastName)
                                .image(image)
                                .reference(reference)
                                .build())
                        .build())
                .classe(ClasseDTO.builder()
                        .id(classeId)
                        .name(classeName)
                        .grade(GradeDTO.builder()
                                .section(classeSection)
                                .build())
                        .build())
                .isArchived(isArchived)
                .build();
    }
}
