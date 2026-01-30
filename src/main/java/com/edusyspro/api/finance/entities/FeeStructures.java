package com.edusyspro.api.finance.entities;

import com.edusyspro.api.finance.enums.PaymentFrequency;
import com.edusyspro.api.model.ClasseEntity;
import com.edusyspro.api.model.Grade;
import com.edusyspro.api.utils.Datetime;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"classe"})
@Table(name = "fee_structures", schema = "finance")
public class FeeStructures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "classe_id", referencedColumnName = "id")
    private ClasseEntity classe;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private FeeCategories category;
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate validFrom;  // When this pricing starts
    private LocalDate validTo;    // When this pricing ends (null = current)
    private Boolean isActive;

    @Enumerated
    private PaymentFrequency frequency;

    private Integer numberOfInstallments;
    private Integer dueDate;    //What day date (for instance, 10th) of each month
    private ZonedDateTime createdAt;

    @PrePersist
    public void preInsert() {
        this.createdAt = Datetime.brazzavilleDatetime();
    }
}
