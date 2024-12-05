package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.TeacherDTO;
import com.edusyspro.api.model.Address;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.School;
import com.edusyspro.api.model.enums.Gender;
import com.edusyspro.api.model.enums.Status;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

public record TeacherEssential(
        UUID id,
        Individual personalInfo,
        LocalDate hireDate,
        double salaryByHour,
        UUID schoolId,
        String schoolName,
        ZonedDateTime createdAt,
        ZonedDateTime modifyAt
){

    public TeacherDTO toTeacher() {
        return TeacherDTO.builder()
                .id(id)
                .personalInfo(personalInfo)
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
