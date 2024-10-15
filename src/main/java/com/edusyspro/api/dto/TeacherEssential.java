package com.edusyspro.api.dto;

import com.edusyspro.api.model.Address;
import com.edusyspro.api.model.School;
import com.edusyspro.api.model.TeacherClassCourse;
import com.edusyspro.api.model.enums.Gender;
import com.edusyspro.api.model.enums.Status;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public record TeacherEssential(
        UUID id,
        String firstName,
        String lastName,
        String maidenName,
        Status status,
        LocalDate birthDate,
        String cityOfBirth,
        String nationality,
        Gender gender,
        Address address,
        String emailId,
        String telephone,
        LocalDate hireDate,
        List<TeacherClassCourse> teacherClassCourses,
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
                .cityOfBirth(cityOfBirth)
                .gender(gender)
                .address(address)
                .emailId(emailId)
                .telephone(telephone)
                .hireDate(hireDate)
                .teacherClassCourses(teacherClassCourses)
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
