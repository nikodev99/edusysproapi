package com.edusyspro.api.dto;

import com.edusyspro.api.model.Address;
import com.edusyspro.api.model.Course;
import com.edusyspro.api.model.School;
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
        Gender gender,
        Address address,
        String emailId,
        String telephone,
        LocalDate hireDate,
        List<Course> courses,
        double salaryByHour,
        School school,
        ZonedDateTime createdAt,
        ZonedDateTime modifyAt
) {
}
