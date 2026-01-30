package com.edusyspro.api.finance.entities;

import com.edusyspro.api.finance.enums.BudgetPeriodStatus;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.School;
import com.edusyspro.api.utils.Datetime;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "budget_periods", schema = "finance")
public class BudgetPeriods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private School school;

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated
    private BudgetPeriodStatus status;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private Individual createdBy;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "approved_by", referencedColumnName = "id")
    private Individual approvedBy;

    private ZonedDateTime approvedAt;
    private ZonedDateTime createdAt;

    @PrePersist
    public void preInsert() {
        this.createdAt = Datetime.brazzavilleDatetime();
    }
}
