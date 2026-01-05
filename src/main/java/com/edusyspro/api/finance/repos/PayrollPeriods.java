package com.edusyspro.api.finance.repos;

import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.School;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "payroll_periods", schema = "finance")
public class PayrollPeriods {
    private Long id;
    private School school;
    private String periodName;
    private String periodCode;
    private LocalDate startDate;
    private LocalDate endDate;
    private ZonedDateTime openedDate;
    private ZonedDateTime approvedDate;
    private LocalDate paidDate;
    private ZonedDateTime closedDate;
    private Individual createdBy;
    private Individual approvedBy;
    private Individual closedBy;
    private ZonedDateTime createdAt;
}
