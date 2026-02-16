package com.edusyspro.api.finance.dto.post;

import com.edusyspro.api.finance.entities.Invoice;
import com.edusyspro.api.finance.entities.Payments;
import com.edusyspro.api.finance.enums.PaymentGateway;
import com.edusyspro.api.finance.enums.PaymentMethod;
import com.edusyspro.api.finance.enums.PaymentStatus;
import com.edusyspro.api.model.EnrollmentEntity;
import com.edusyspro.api.model.Individual;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentPost {
    private Long student;

    @NotNull(message = "Invoice ID is required")
    private Long invoice;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amountPaid;

    private String currency = "XAF";
    private String transactionId = UUID.randomUUID().toString();

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;
    private PaymentGateway paymentGateway;

    private Long processedBy;
    private Long createdBy;
    private String notes;

    public Payments buildPayment() {
        return Payments.builder()
                .student(EnrollmentEntity.builder().id(student).build())
                .invoice(Invoice.builder().id(invoice).build())
                .amountPaid(amountPaid)
                .paymentDate(ZonedDateTime.now())
                .currency(currency != null ? currency : "XAF")
                .transactionId(transactionId)
                .paymentMethod(paymentMethod)
                .paymentGateway(paymentGateway)
                .processedBy(Individual.builder().id(processedBy).build())
                .createdBy(Individual.builder().id(createdBy).build())
                .status(PaymentStatus.COMPLETED)
                .notes(notes)
                .createdAt(ZonedDateTime.now())
                .build();
    }
}
