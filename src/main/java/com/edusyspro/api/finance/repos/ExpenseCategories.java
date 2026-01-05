package com.edusyspro.api.finance.repos;

import com.edusyspro.api.model.School;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "expense_categories", schema = "finance")
public class ExpenseCategories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private School school;

    private String name;
    private String code;
    private boolean requireApproval;
    private BigDecimal approvalThreshold;
    private BigDecimal maxAmount;
    private Boolean isActive;
}
