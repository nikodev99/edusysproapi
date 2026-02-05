package com.edusyspro.api.finance.controller;

import com.edusyspro.api.auth.response.MessageResponse;
import com.edusyspro.api.auth.user.CustomUserDetails;
import com.edusyspro.api.downloads.FinanceFileGeneratorService;
import com.edusyspro.api.resource.FileGenerationException;
import com.edusyspro.api.resource.io.MimeType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/download")
public class DownloadsController {
    private final FinanceFileGeneratorService financeFileGeneratorService;

    public DownloadsController(FinanceFileGeneratorService financeFileGeneratorService) {
        this.financeFileGeneratorService = financeFileGeneratorService;
    }

    @GetMapping("/invoice/{invoiceId}/{schoolId}")
    ResponseEntity<?> downloadInvoice(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable Long invoiceId,
            @PathVariable() String schoolId
    ) {
        try {
            byte[] file = financeFileGeneratorService.generateInvoice(user, invoiceId, schoolId);
            HttpHeaders headers = getFileHeader("invoice-" + invoiceId);
            return new ResponseEntity<>(file, headers, HttpStatus.OK);
        }catch (FileGenerationException e){
            return ResponseEntity.badRequest().body(MessageResponse.builder()
                    .message(e.getMessage())
                    .isError(true)
                    .timestamp(Instant.now().toString())
                    .build());
        }
    }

    @GetMapping("payment/{paymentId}/{schoolId}")
    ResponseEntity<?> downloadPaymentReceipt(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable String paymentId,
            @PathVariable String schoolId
    ) {
        try {
            byte[] file = financeFileGeneratorService.generatePaymentReceipt(user, paymentId, schoolId);
            HttpHeaders headers = getFileHeader("payment-receipt-" + paymentId);
            return new ResponseEntity<>(file, headers, HttpStatus.OK);
        }catch (FileGenerationException e) {
            return ResponseEntity.badRequest().body(MessageResponse.builder()
                    .message(e.getMessage())
                    .isError(true)
                    .timestamp(Instant.now().toString())
                    .build());
        }
    }

    private HttpHeaders getFileHeader(String fileName) {
        HttpHeaders headers = new HttpHeaders();
        MimeType type = MimeType.PDF;
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileName+"."+type.getPrimaryExtension().orElse("pdf"));
        headers.add(HttpHeaders.CONTENT_TYPE, type.getMimeType());
        return headers;
    }
}
