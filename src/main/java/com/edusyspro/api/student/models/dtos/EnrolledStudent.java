package com.edusyspro.api.student.models.dtos;

import com.edusyspro.api.classes.ClasseEntity;
import com.edusyspro.api.entities.enums.Gender;
import com.edusyspro.api.entities.enums.Section;
import com.edusyspro.api.school.entities.AcademicYear;
import com.edusyspro.api.school.entities.Grade;
import com.edusyspro.api.student.entities.StudentEntity;
import com.edusyspro.api.student.models.Enrollment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnrolledStudent {
    private UUID id;
    private AcademicYear academicYear;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String emailId;
    private LocalDate birthDate;
    private String birthCity;
    private String nationality;
    private String reference;
    private String image;
    private ZonedDateTime lastEnrolledDate;
    private String classe;
    private Section grade;

    public Enrollment populateStudent(EnrolledStudent e) {
        return Enrollment.builder()
                .academicYear(e.academicYear)
                .student(StudentEntity.builder()
                        .id(e.id)
                        .firstName(e.firstName)
                        .lastName(e.lastName)
                        .gender(e.gender)
                        .emailId(e.emailId)
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
