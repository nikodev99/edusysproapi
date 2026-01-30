package com.edusyspro.api.finance.dto;

import java.math.BigDecimal;

public record PaymentSummary(
        BigDecimal totalOutstanding,
        BigDecimal totalPaidThisYear,
        Integer overdueInvoices
) {
}
