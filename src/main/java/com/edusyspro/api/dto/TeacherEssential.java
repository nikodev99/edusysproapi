package com.edusyspro.api.dto;

import com.edusyspro.api.model.Address;
import com.edusyspro.api.model.School;
import com.edusyspro.api.model.enums.Gender;
import com.edusyspro.api.model.enums.Status;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

public record TeacherEssential(
    UUID id,
    String firstName,
    String lastName,
    String maidenName,
    Status status,
    LocalDate birthDate,
    Gender gender,
    Address address,
    String emailId,
    String telephone,
    LocalDate hireDate,
    double salaryByHour,
    UUID schoolId,
    String schoolName,
    ZonedDateTime createdAt,
    ZonedDateTime modifyAt
){

    public Teacher toTeacher() {
        return Teacher.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .maidenName(maidenName)
                .status(status)
                .birthDate(birthDate)
                .gender(gender)
                .address(address)
                .emailId(emailId)
                .telephone(telephone)
                .hireDate(hireDate)
                .salaryByHour(salaryByHour)
                .school(School.builder()
                        .id(schoolId)
                        .name(schoolName)
                        .build()
                )
                .createdAt(createdAt)
                .modifyAt(modifyAt)
                .build();

    }
}
