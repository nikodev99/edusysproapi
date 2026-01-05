package com.edusyspro.api.finance.repos;

import com.edusyspro.api.finance.enums.ApprovalStatus;
import com.edusyspro.api.finance.enums.PaymentMethod;
import com.edusyspro.api.finance.enums.PaymentStatus;
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
@Table(name = "expenses", schema = "finance")
public class Expenses {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String expenseReference;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "expense_category_id", referencedColumnName = "id")
    private ExpenseCategories category;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "budget_id", referencedColumnName = "id")
    private Budgets budget;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "vendor_id", referencedColumnName = "id")
    private Vendors vendor;

    private ZonedDateTime expenseDate;
    private String description;
    private BigDecimal amount;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;
    private PaymentMethod paymentMethod;
    private PaymentStatus status;

    @Enumerated
    private ApprovalStatus approvalStatus;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "approved_by", referencedColumnName = "id")
    private Individual approvedBy;

    private ZonedDateTime approvedDate;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "createdBy", referencedColumnName = "id")
    private Individual createdBy;

    private ZonedDateTime createdAt;
    private String receiptUrl;
}
