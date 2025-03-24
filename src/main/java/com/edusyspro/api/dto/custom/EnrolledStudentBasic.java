package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.EnrollmentDTO;
import com.edusyspro.api.dto.StudentDTO;
import com.edusyspro.api.model.Individual;

import java.util.UUID;

public record EnrolledStudentBasic (
    long enrollmentId,
    UUID id,
    String firstName,
    String lastName,
    String image,
    String reference
) {
    public EnrollmentDTO toDTO() {
        return EnrollmentDTO.builder()
                .id(enrollmentId)
                .student(StudentDTO.builder()
                        .id(id)
                        .personalInfo(Individual.builder()
                                .firstName(firstName)
                                .lastName(lastName)
                                .image(image)
                                .build())
                        .reference(reference)
                        .build())
                .build();
    }
}
