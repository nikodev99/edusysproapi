package com.edusyspro.api.finance.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponseMessage {
    private String paymentId;
    private Object data;
    private String status;
    private String message;
    private boolean success;
}
