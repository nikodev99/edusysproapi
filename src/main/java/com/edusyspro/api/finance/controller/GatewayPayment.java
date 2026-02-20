package com.edusyspro.api.finance.controller;

import com.edusyspro.api.auth.user.CustomUserDetails;
import com.edusyspro.api.finance.dto.post.CreditCardPostRequest;
import com.edusyspro.api.finance.dto.post.MobileMoneyPostRequest;
import com.edusyspro.api.finance.dto.response.PaymentResponseMessage;
import com.edusyspro.api.finance.services.GatewayPaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("gateway")
public class GatewayPayment {
    private final GatewayPaymentService gatewayPaymentService;

    public GatewayPayment(GatewayPaymentService gatewayPaymentService) {
        this.gatewayPaymentService = gatewayPaymentService;
    }

    @PostMapping("/mtn-momo")
    ResponseEntity<?> mtnPayment(
            @AuthenticationPrincipal CustomUserDetails connectedUser,
            @Valid @RequestBody MobileMoneyPostRequest request
    ) {
        return ResponseEntity.ok().body(PaymentResponseMessage.builder()
                .data(connectedUser.getUsername())
                .paymentId(UUID.randomUUID().toString())
                .success(true)
                .build());
    }

    @PostMapping("/airtel-momo")
    ResponseEntity<?> airtelPayment(
            @AuthenticationPrincipal CustomUserDetails connectedUser,
            @Valid @RequestBody MobileMoneyPostRequest request
    ) {
        return ResponseEntity.ok().body(PaymentResponseMessage.builder()
                .paymentId(UUID.randomUUID().toString())
                .data("Donné venant de airtel momo")
                .success(true)
                .build());
    }

    @PostMapping("/credit-card")
    ResponseEntity<?> bankPayment(
            @AuthenticationPrincipal CustomUserDetails connectedUser,
            @Valid @RequestBody CreditCardPostRequest request
    ) {
        return ResponseEntity.ok().body(PaymentResponseMessage.builder()
                .paymentId(UUID.randomUUID().toString())
                .data("Donné venant de la carte de credit card")
                .success(true)
                .build());
    }
}
