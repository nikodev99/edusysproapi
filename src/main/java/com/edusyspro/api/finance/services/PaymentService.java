package com.edusyspro.api.finance.services;

import com.edusyspro.api.finance.dto.PaymentHistory;
import com.edusyspro.api.finance.dto.request.PaymentRequest;
import com.edusyspro.api.finance.repos.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public PaymentHistory getPayment(String paymentId) {
        return paymentRepository.findPaymentById(UUID.fromString(paymentId))
                .map(PaymentRequest::toHistory)
                .orElse(PaymentHistory.builder().build());
    }
}
