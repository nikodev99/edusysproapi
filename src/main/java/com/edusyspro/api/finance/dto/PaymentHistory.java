package com.edusyspro.api.finance.dto;

import com.edusyspro.api.dto.EnrollmentDTO;
import com.edusyspro.api.dto.GuardianDTO;
import com.edusyspro.api.dto.custom.IndividualBasic;
import com.edusyspro.api.finance.entities.Payments;
import com.edusyspro.api.finance.enums.PaymentGateway;
import com.edusyspro.api.finance.enums.PaymentMethod;
import com.edusyspro.api.finance.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentHistory {
    private UUID paymentId;
    private String voucherNumber;
    private String receiptNumber;
    private ZonedDateTime paymentDate;
    private EnrollmentDTO enrolledStudent;
    private GuardianDTO guardian;
    private InvoiceDTO invoice;
    private BigDecimal amountPaid;
    private String currency;
    private String transactionId;
    private PaymentGateway paymentGateway;
    private PaymentMethod paymentMethod;
    private PaymentStatus status;
    private IndividualBasic processedBy;
    private IndividualBasic createBy;
    private String notes;
    private ZonedDateTime createdAt;
}
