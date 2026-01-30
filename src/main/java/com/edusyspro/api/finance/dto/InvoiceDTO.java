package com.edusyspro.api.finance.dto;

import com.edusyspro.api.dto.EnrollmentDTO;
import com.edusyspro.api.dto.custom.IndividualBasic;
import com.edusyspro.api.finance.enums.InvoiceStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceDTO {
    private Long invoiceId;
    private Long enrollmentId;
    private EnrollmentDTO enrolledStudent;
    private ZonedDateTime invoiceDate;
    private LocalDate dueDate;
    private String invoiceNumber;
    private BigDecimal subTotalAmount;
    private BigDecimal discount;
    private BigDecimal taxAmount;
    private BigDecimal amountPaid;
    private BigDecimal totalAmount;
    private BigDecimal balanceDue;
    private InvoiceStatus status;
    private Boolean isOverdue;
    private List<InvoiceItemDto> items;
    private IndividualBasic issueBy;
}
