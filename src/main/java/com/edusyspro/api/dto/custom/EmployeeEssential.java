package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.EmployeeContractDTO;
import com.edusyspro.api.dto.EmployeeDTO;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.School;
import com.edusyspro.api.model.enums.ContractStatus;
import com.edusyspro.api.model.enums.ContractType;
import com.edusyspro.api.model.enums.StaffRole;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

public record EmployeeEssential(
        UUID id,
        Individual personalInfo,
        Long contractId,
        StaffRole role,
        String jobTitle,
        BigDecimal salary,
        ContractType contractType,
        ContractStatus active,
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
                .contract(EmployeeContractDTO.builder()
                        .id(contractId)
                        .role(role)
                        .jobTitle(jobTitle)
                        .monthlySalary(salary)
                        .contractType(contractType)
                        .status(active)
                        .startDate(hireDate)
                        .build())
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
