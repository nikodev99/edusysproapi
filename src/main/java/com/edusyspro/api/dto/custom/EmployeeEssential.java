package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.EmployeeDTO;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.School;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

public record EmployeeEssential(
        UUID id,
        Individual personalInfo,
        String jobTitle,
        BigDecimal salary,
        String contractType,
        boolean active,
        LocalDate hireDate,
        UUID schoolId,
        String schoolName,
        String schoolAbbr,
        ZonedDateTime createdAt,
        ZonedDateTime modifyAt
) {
    public EmployeeDTO toDto() {
        return EmployeeDTO.builder()
                .id(id)
                .personalInfo(personalInfo)
                .jobTitle(jobTitle)
                .salary(salary)
                .contractType(contractType)
                .active(active)
                .hireDate(hireDate)
                .school(School.builder()
                        .id(schoolId)
                        .name(schoolName)
                        .abbr(schoolAbbr)
                        .build())
                .createdAt(createdAt)
                .modifyAt(modifyAt)
                .build();
    }
}
