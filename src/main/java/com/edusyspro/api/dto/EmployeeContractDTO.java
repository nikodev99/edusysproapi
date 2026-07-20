package com.edusyspro.api.dto;

import com.edusyspro.api.model.EmployeeContracts;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.enums.ContractStatus;
import com.edusyspro.api.model.enums.ContractType;
import com.edusyspro.api.model.enums.SalaryBasis;
import com.edusyspro.api.model.enums.StaffRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeContractDTO {
    private Long id;
    private StaffRole role;
    private String contractNumber;
    private String jobTitle;
    private ContractType contractType;
    private SalaryBasis salaryBasis;
    private BigDecimal salaryByHour;
    private BigDecimal monthlySalary;
    private String currency;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isTrialPeriod;
    private LocalDate trialEndDate;
    private ContractStatus status;
    private String terminationReason;
    private String bankName;
    private String bankAccount;
    private String mobileMoneyNumber;
    private String cnssNumber;
    private Individual createdBy;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;

    public EmployeeContracts toEntity() {
        return EmployeeContracts.builder()
                .id(id)
                .role(role)
                .contractNumber(contractNumber)
                .jobTitle(jobTitle)
                .contractType(contractType)
                .salaryBasis(salaryBasis)
                .salaryByHour(salaryByHour)
                .monthlySalary(monthlySalary)
                .currency(currency)
                .startDate(startDate)
                .endDate(endDate)
                .isTrialPeriod(isTrialPeriod)
                .trialEndDate(trialEndDate)
                .status(status)
                .terminationReason(terminationReason)
                .bankName(bankName)
                .bankAccount(bankAccount)
                .mobileMoneyNumber(mobileMoneyNumber)
                .cnssNumber(cnssNumber)
                .createdBy(createdBy)
                .createdAt(createdAt)
                .modifyAt(modifyAt)
                .build();
    }

    public static EmployeeContractDTO fromEntity(EmployeeContracts contract) {
        return EmployeeContractDTO.builder()
                .id(contract.getId())
                .role(contract.getRole())
                .jobTitle(contract.getJobTitle())
                .salaryByHour(contract.getSalaryByHour())
                .startDate(contract.getStartDate())
                .build();
    }

    public EmployeeContracts mergeEmployeeContract() {
        return EmployeeContracts.builder()
                .id(id)
                .build();
    }
}
