package com.edusyspro.api.model;

import com.edusyspro.api.model.enums.ContractStatus;
import com.edusyspro.api.model.enums.ContractType;
import com.edusyspro.api.model.enums.SalaryBasis;
import com.edusyspro.api.model.enums.StaffRole;
import com.edusyspro.api.utils.Datetime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "employee_contracts")
public class EmployeeContracts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StaffRole role;

    private String contractNumber;

    private String jobTitle;

    @Enumerated(EnumType.STRING)
    private ContractType contractType;

    @Enumerated(EnumType.STRING)
    private SalaryBasis salaryBasis;

    @Column(precision = 10, scale = 2)
    private BigDecimal salaryByHour;

    @Column(precision = 10, scale = 2)
    private BigDecimal monthlySalary;

    private String currency;

    private LocalDate startDate;
    private LocalDate endDate;

    private boolean isTrialPeriod;
    private LocalDate trialEndDate;

    @Enumerated(EnumType.STRING)
    private ContractStatus status;

    @Column(length = 5000)
    private String terminationReason;

    private String bankName;
    private String bankAccount;
    private String mobileMoneyNumber;
    private String cnssNumber;

    @ManyToOne(cascade = {CascadeType.DETACH})
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private Individual createdBy;

    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;

    @PrePersist
    public void prePersist() {
        salaryBasis = SalaryBasis.HOURLY;
        currency = "XAF";
        isTrialPeriod = false;
        createdAt = Datetime.brazzavilleDatetime();
        modifyAt = Datetime.brazzavilleDatetime();
    }
}
