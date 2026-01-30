package com.edusyspro.api.finance.controller;

import com.edusyspro.api.auth.user.CustomUserDetails;
import com.edusyspro.api.finance.dto.*;
import com.edusyspro.api.finance.dto.post.PaymentPost;
import com.edusyspro.api.finance.dto.response.PaymentResponse;
import com.edusyspro.api.finance.services.GuardianPaymentService;
import com.edusyspro.api.service.interfaces.GuardianService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/guardian/payment")
public class GuardianPaymentController {
    private final GuardianPaymentService guardianPaymentService;
    private final GuardianService guardianService;

    public GuardianPaymentController(GuardianPaymentService guardianPaymentService, GuardianService guardianService) {
            this.guardianPaymentService = guardianPaymentService;
            this.guardianService = guardianService;
    }

    @GetMapping("/summary/{guardianId}")
    ResponseEntity<PaymentSummary> getPaymentSummary(@PathVariable String guardianId, @RequestParam String academicYear) {
        return ResponseEntity.ok(guardianPaymentService.getPaymentSummary(guardianId, academicYear));
    }

    @GetMapping("/invoices/{guardianId}/{schoolId}")
    ResponseEntity<List<InvoiceDTO>> getGuardianInvoices(
            @PathVariable String guardianId,
            @PathVariable String schoolId
    ) {
        return ResponseEntity.ok(guardianPaymentService.getGuardianInvoices(guardianId, schoolId));
    }

    @GetMapping("/active_invoice/{guardianId}")
    ResponseEntity<InvoiceDTO> getGuardianActiveInvoice(@PathVariable String guardianId, @RequestParam String academicYear) {
        return ResponseEntity.ok(guardianPaymentService.getActiveInvoice(guardianId, academicYear));
    }

    @GetMapping("/history/{guardianId}")
    ResponseEntity<List<PaymentHistory>> getGuardianPaymentHistory(@PathVariable String guardianId, @RequestParam String academicYear) {
        return ResponseEntity.ok(guardianPaymentService.getGuardianPaymentHistory(guardianId, academicYear));
    }

    @PostMapping("/initiate")
    ResponseEntity<PaymentResponse> initiatePayment(
            @AuthenticationPrincipal CustomUserDetails guardian,
            @Valid @RequestBody PaymentPost paymentRequest
    ) {
            UUID guardianId = guardianService.getGuardianId(guardian.getPersonalInfoId());
        return ResponseEntity.ok(guardianPaymentService.createPayment(guardianId.toString(), paymentRequest));
    }
}
