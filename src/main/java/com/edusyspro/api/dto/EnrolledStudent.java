package com.edusyspro.api.dto;

import com.edusyspro.api.model.ClasseEntity;
import com.edusyspro.api.model.enums.Gender;
import com.edusyspro.api.model.enums.Section;
import com.edusyspro.api.model.AcademicYear;
import com.edusyspro.api.model.Grade;
import com.edusyspro.api.model.StudentEntity;
import lombok.Builder;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@Builder
public record EnrolledStudent(
        UUID id, AcademicYear academicYear, String firstName, String lastName, Gender gender, String emailId,
        LocalDate birthDate, String birthCity, String nationality, String reference, String image, ZonedDateTime lastEnrolledDate,
        String classe, Section grade, String dadName, String momName
) {

    public Enrollment populateStudent(EnrolledStudent e) {
        return Enrollment.builder()
                .academicYear(e.academicYear)
                .student(StudentEntity.builder()
                        .id(e.id)
                        .firstName(e.firstName)
                        .lastName(e.lastName)
                        .gender(e.gender)
                        .emailId(e.emailId)
                        .dadName(e.dadName)
                        .momName(e.momName)
                        .birthDate(e.birthDate)
                        .birthCity(e.birthCity)
                        .nationality(e.nationality)
                        .reference(e.reference)
                        .image(e.image)
                        .build())
                .enrollmentDate(e.lastEnrolledDate)
                .classe(ClasseEntity.builder()
                        .name(e.classe)
                        .grade(Grade.builder()
                                .section(e.grade)
                                .build())
                        .build())
                .build();
    }
}
