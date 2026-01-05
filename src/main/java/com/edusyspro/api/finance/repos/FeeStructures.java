package com.edusyspro.api.finance.repos;

import com.edusyspro.api.finance.enums.PaymentFrequency;
import com.edusyspro.api.model.ClasseEntity;
import com.edusyspro.api.model.Grade;
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
@Table(name = "fee_structures", schema = "finance")
public class FeeStructures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "grade_id", referencedColumnName = "id")
    private Grade grade;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "classe_id", referencedColumnName = "id")
    private ClasseEntity classe;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private FeeCategories category;
    private BigDecimal amount;

    @Enumerated
    private PaymentFrequency frequency;

    private Integer numberOfInstallments;
    private LocalDate dueDate;
    private ZonedDateTime createdAt;
}
