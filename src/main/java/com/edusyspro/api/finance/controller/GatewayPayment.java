package com.edusyspro.api.finance.controller;

import com.edusyspro.api.finance.dto.response.PaymentResponseMessage;
import com.edusyspro.api.finance.services.GatewayPaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("gateway")
public class GatewayPayment {
    private final GatewayPaymentService gatewayPaymentService;

    public GatewayPayment(GatewayPaymentService gatewayPaymentService) {
        this.gatewayPaymentService = gatewayPaymentService;
    }

    @PostMapping()
    ResponseEntity<?> mtnPayment() {
        return ResponseEntity.ok().body(PaymentResponseMessage.builder()
                .data("Donné venant de mtn momo")
                .success(true)
                .build());
    }

    @PostMapping()
    ResponseEntity<?> airtelPayment() {
        return ResponseEntity.ok().body(PaymentResponseMessage.builder()
                .data("Donné venant de airtel momo")
                .success(true)
                .build());
    }

    @PostMapping()
    ResponseEntity<?> bankPayment() {
        return ResponseEntity.ok().body(PaymentResponseMessage.builder()
                .data("Donné venant de mtn momo")
                .success(true)
                .build());
    }
}
