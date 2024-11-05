package com.edusyspro.api.dto;

import com.edusyspro.api.model.*;
import com.edusyspro.api.model.enums.Gender;
import com.edusyspro.api.model.enums.Section;
import lombok.Builder;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@Builder
public record EnrolledStudent(
        UUID id,
        AcademicYear academicYear,
        String firstName,
        String lastName,
        Gender gender,
        String emailId,
        LocalDate birthDate,
        String birthCity,
        String nationality,
        String reference,
        String image,
        ZonedDateTime lastEnrolledDate,
        int classeId,
        String classe,
        String classeCategory,
        Section grade,
        double monthCost,
        String dadName,
        String momName
) {

    public static Enrollment populateStudent(EnrolledStudent e) {
        return Enrollment.builder()
                .academicYear(e.academicYear)
                .student(StudentEntity.builder()
                        .id(e.id)
                        .personalInfo(Individual.builder()
                                .firstName(e.firstName)
                                .lastName(e.lastName)
                                .gender(e.gender)
                                .emailId(e.emailId)
                                .birthDate(e.birthDate)
                                .birthCity(e.birthCity)
                                .nationality(e.nationality)
                                .image(e.image)
                                .build()
                        )
                        .dadName(e.dadName)
                        .momName(e.momName)
                        .reference(e.reference)
                        .build())
                .enrollmentDate(e.lastEnrolledDate)
                .classe(ClasseEntity.builder()
                        .id(e.classeId)
                        .name(e.classe)
                        .category(e.classeCategory)
                        .grade(Grade.builder()
                                .section(e.grade)
                                .build())
                        .monthCost(e.monthCost)
                        .build())
                .build();
    }
}
