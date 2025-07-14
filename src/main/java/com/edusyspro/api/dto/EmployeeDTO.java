package com.edusyspro.api.dto;

import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.School;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDTO {
    private UUID id;
    private Individual personalInfo;
    private String jobTitle;
    private BigDecimal salary;
    private String contractType;
    private boolean active;
    private LocalDate hireDate;
    private School school;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;
}
