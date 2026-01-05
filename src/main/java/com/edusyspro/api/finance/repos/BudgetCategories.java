package com.edusyspro.api.finance.repos;

import com.edusyspro.api.finance.enums.BudgetCategoryType;
import com.edusyspro.api.model.School;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "budget_categories", schema = "finance")
public class BudgetCategories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "school_id", referencedColumnName = "id")
    private School school;

    private String name;
    private String code;

    @Enumerated
    private BudgetCategoryType categoryType;

    private String accountCode;
    private String description;
    private Boolean isActive;
}
