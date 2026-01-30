package com.edusyspro.api.finance.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface OutstandingInvoice {
    BigDecimal getBalanceDue();
    LocalDate getDueDate();
    UUID getId();
    BigDecimal getAmountPaid();
}
