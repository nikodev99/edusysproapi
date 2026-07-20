package com.edusyspro.api.dto;

import com.edusyspro.api.model.Employee;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.School;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDTO {
    private UUID id;
    private Individual personalInfo;
    private EmployeeContractDTO contract;
    private School school;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;

    public static EmployeeDTO fromEntity(Employee savedEmployee) {
        return EmployeeDTO.builder()
                .id(savedEmployee.getId())
                .personalInfo(savedEmployee.getPersonalInfo())
                .contract(EmployeeContractDTO.fromEntity(savedEmployee.getContract()))
                .school(savedEmployee.getSchool())
                .createdAt(savedEmployee.getCreatedAt())
                .modifyAt(savedEmployee.getModifyAt())
                .build();
    }

    public Employee toEntity() {
        return Employee.builder()
                .id(id)
                .personalInfo(personalInfo)
                .contract(contract.toEntity())
                .school(school)
                .createdAt(createdAt)
                .modifyAt(modifyAt)
                .build();
    }
}
