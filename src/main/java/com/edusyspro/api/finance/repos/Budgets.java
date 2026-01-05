package com.edusyspro.api.finance.repos;

import com.edusyspro.api.model.Department;
import com.edusyspro.api.model.Individual;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "budgets", schema = "finance")
public class Budgets {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "period_id", referencedColumnName = "id")
    private BudgetPeriods period;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private BudgetCategories category;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    private BigDecimal budgetedAmount;
    private BigDecimal actualAmount;
    private BigDecimal varianceAmount;
    private BigDecimal variancePercentage;
    private String description;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private Individual createdBy;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "approved_by", referencedColumnName = "id")
    private Individual approvedBy;

    private ZonedDateTime approvedAt;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifyAt;
}
