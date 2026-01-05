package com.edusyspro.api.finance.repos;

import com.edusyspro.api.finance.enums.PaymentGateway;
import com.edusyspro.api.finance.enums.PaymentMethod;
import com.edusyspro.api.finance.enums.PaymentStatus;
import com.edusyspro.api.model.EnrollmentEntity;
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
@Table(name = "payments", schema = "finance")
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private EnrollmentEntity student;

    @Column(length = 100, unique = true)
    private String voucherNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id", referencedColumnName = "id")
    private Invoice invoice;

    private ZonedDateTime paymentDate;

    @Enumerated
    private PaymentMethod paymentMethod;

    private BigDecimal amountPaid;
    private String currency;
    private String transactionId;

    @Enumerated
    private PaymentGateway paymentGateway;

    @Enumerated
    private PaymentStatus status;

    @Column(length = 50, unique = true)
    private String receiptNumber;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "processed_by", referencedColumnName = "id")
    private Individual processedBy;

    @Column(length = 2000)
    private String notes;

    private ZonedDateTime createdAt;
}
