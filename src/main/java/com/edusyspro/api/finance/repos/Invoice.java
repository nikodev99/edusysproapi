package com.edusyspro.api.finance.repos;

import com.edusyspro.api.finance.enums.InvoiceStatus;
import com.edusyspro.api.model.EnrollmentEntity;
import com.edusyspro.api.model.Individual;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "invoice", schema = "finance")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private EnrollmentEntity student;

    private ZonedDateTime invoiceDate;
    private ZonedDateTime dueDate;
    private BigDecimal subTotalAmount;
    private BigDecimal discount;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;
    private BigDecimal amountPaid;
    private BigDecimal balanceDue;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<InvoiceLine> invoiceLines;

    @Enumerated
    private InvoiceStatus status;

    @Column(length = 2000)
    private String notes;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private Individual createdBy;

    private ZonedDateTime createdAt;
}
