package com.edusyspro.api.dto.custom;

import com.edusyspro.api.dto.EmployeeContractDTO;
import com.edusyspro.api.dto.EmployeeDTO;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.enums.Gender;
import com.edusyspro.api.model.enums.StaffRole;
import com.edusyspro.api.model.enums.Status;

import java.time.LocalDate;
import java.util.UUID;

public record EmployeeIndividual(
        UUID employeeId,
        Long personalInfoId,
        String firstName,
        String lastName,
        Gender gender,
        Status status,
        String emailId,
        String telephone,
        String reference,
        String image,
        LocalDate hireDate,
        StaffRole jobTitle
) {
    public EmployeeDTO toEmployee() {
        return EmployeeDTO.builder()
                .id(employeeId)
                .personalInfo(Individual.builder()
                        .id(personalInfoId)
                        .firstName(firstName)
                        .lastName(lastName)
                        .gender(gender)
                        .status(status)
                        .emailId(emailId)
                        .telephone(telephone)
                        .reference(reference)
                        .image(image)
                        .build())
                .contract(EmployeeContractDTO.builder()
                        .startDate(hireDate)
                        .role(jobTitle)
                        .build())
                .build();
    }
}
