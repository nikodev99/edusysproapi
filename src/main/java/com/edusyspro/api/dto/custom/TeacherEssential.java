package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.EmployeeContractDTO;
import com.edusyspro.api.dto.TeacherDTO;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.School;
import com.edusyspro.api.model.enums.AffiliationStatus;
import com.edusyspro.api.model.enums.StaffRole;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

public record TeacherEssential(
        UUID id,
        Individual personalInfo,
        Long contractId,
        StaffRole role,
        String jobTitle,
        LocalDate hireDate,
        BigDecimal salaryByHour,
        AffiliationStatus status,
        UUID schoolId,
        String schoolName,
        ZonedDateTime createdAt,
        ZonedDateTime modifyAt
){

    public TeacherDTO toTeacher() {
        return TeacherDTO.builder()
                .id(id)
                .personalInfo(personalInfo)
                .contract(EmployeeContractDTO.builder()
                        .id(contractId)
                        .startDate(hireDate)
                        .salaryByHour(salaryByHour)
                        .role(role)
                        .jobTitle(jobTitle)
                        .build())
                .status(status)
                .school(School.builder()
                        .id(schoolId)
                        .name(schoolName)
                        .build())
                .createdAt(createdAt)
                .modifyAt(modifyAt)
                .build();
    }
}
