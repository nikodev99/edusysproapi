package com.edusyspro.api.finance.entities;

import com.edusyspro.api.finance.enums.InvoiceStatus;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.utils.Datetime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    @JoinColumn(name = "assessment_id", referencedColumnName = "id")
    private FeeAssessment feeAssessment;

    private ZonedDateTime invoiceDate;

    @Column(name = "invoice_number", unique = true, nullable = false, length = 100)
    private String invoiceNumber;
    private LocalDate dueDate;
    private BigDecimal subTotalAmount;
    private BigDecimal discount;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;
    private BigDecimal amountPaid;
    private BigDecimal balanceDue;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.PERSIST)
    private List<InvoiceLine> invoiceLines;

    @Enumerated
    private InvoiceStatus status;

    @Column(length = 2000)
    private String notes;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private Individual createdBy;

    private ZonedDateTime createdAt;

    @PrePersist
    public void preInsert() {
        this.createdAt = Datetime.brazzavilleDatetime();
    }
}
