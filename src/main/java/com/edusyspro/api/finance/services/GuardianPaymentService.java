package com.edusyspro.api.finance.services;

import com.edusyspro.api.exception.sql.NotFountException;
import com.edusyspro.api.finance.dto.*;
import com.edusyspro.api.finance.dto.post.PaymentPost;
import com.edusyspro.api.finance.dto.request.InvoiceLineRequest;
import com.edusyspro.api.finance.dto.request.InvoiceRequest;
import com.edusyspro.api.finance.dto.request.OutstandingInvoice;
import com.edusyspro.api.finance.dto.request.PaymentRequest;
import com.edusyspro.api.finance.dto.response.PaymentResponse;
import com.edusyspro.api.finance.entities.Invoice;
import com.edusyspro.api.finance.entities.Payments;
import com.edusyspro.api.finance.enums.InvoiceStatus;
import com.edusyspro.api.finance.repos.InvoiceRepository;
import com.edusyspro.api.finance.repos.PaymentRepository;
import com.edusyspro.api.utils.Datetime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class GuardianPaymentService {
    private final InvoiceRepository invoiceRepository;
    private final PaymentRepository paymentRepository;
    private final DocumentSequenceService documentSequenceService;

    public GuardianPaymentService(
            InvoiceRepository invoiceRepository,
            PaymentRepository paymentRepository,
            DocumentSequenceService documentSequenceService
    ) {
        this.invoiceRepository = invoiceRepository;
        this.paymentRepository = paymentRepository;
        this.documentSequenceService = documentSequenceService;
    }

    public PaymentSummary getPaymentSummary(String guardianId, String academicYear) {
        List<OutstandingInvoice> outstandingInvoices = invoiceRepository.findOutstandingInvoicesByGuardianId(
                UUID.fromString(guardianId),
                UUID.fromString(academicYear),
                List.of(InvoiceStatus.DRAFT, InvoiceStatus.PARTIALLY_PAID)
        );

        BigDecimal totalOutstanding = outstandingInvoices.stream()
                .map(OutstandingInvoice::getBalanceDue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Integer overdueCount = Math.toIntExact(outstandingInvoices.stream()
                .filter(inv -> inv.getDueDate().isBefore(LocalDate.now()))
                .count());

        BigDecimal totalPaid = Optional.of(outstandingInvoices.get(0))
                .map(OutstandingInvoice::getAmountPaid)
                .orElse(BigDecimal.ZERO);

        return new PaymentSummary(totalOutstanding, totalPaid, overdueCount);
    }

    public List<InvoiceDTO> getGuardianInvoices(String guardianId, String schoolId) {
        List<InvoiceRequest> invoices = invoiceRepository.findAllByGuardian(UUID.fromString(guardianId), UUID.fromString(schoolId));

        return invoices.stream()
                .map(InvoiceRequest::toDto)
                .toList();
    }

    public List<InvoiceDTO> getGuardianInvoicesByAcademicYear(String guardianId, String academicYear) {
        return invoiceRepository.findAllByGuardianByAcademicYear(UUID.fromString(guardianId), UUID.fromString(academicYear)).stream()
                .map(InvoiceRequest::toDto)
                .toList();
    }

    public List<InvoiceDTO> getActiveInvoice(String guardianId, String academicYear) {
        LocalDate now = Datetime.brazzavilleDatetime().toLocalDate();
        return invoiceRepository.findActiveInvoice(
                        UUID.fromString(guardianId),
                        UUID.fromString(academicYear),
                        List.of(InvoiceStatus.PAID, InvoiceStatus.CANCELLED),
                        BigDecimal.ZERO,
                        now, now.plusDays(10)
                ).stream()
                .map(inv -> {
                    InvoiceDTO invoice = inv.toDto();
                    List<InvoiceItemDto> invoiceLines = invoiceRepository.findInvoiceLineByInvoice(inv.invoiceId()).stream()
                            .map(InvoiceLineRequest::toDto)
                            .toList();
                    invoice.setItems(invoiceLines);

                    return invoice;
                })
                .toList();
    }

    public List<PaymentHistory> getGuardianPaymentHistory(String guardianId, String academicYear) {
        List<PaymentRequest> payments = getAllGuardianPayments(guardianId, academicYear);

        return payments.stream()
                .map(PaymentRequest::toHistory)
                .toList();
    }

    @Transactional
    public PaymentResponse createPayment(String schoolId, PaymentPost request) {
        Invoice invoice = invoiceRepository.findById(request.getInvoice())
                .orElseThrow(() -> new NotFountException("Invoice not found"));

        if (request.getAmountPaid().compareTo(invoice.getBalanceDue()) > 0) {
            throw new RuntimeException("Amount must be greater than zero");
        }

        String[] parts = invoice.getInvoiceNumber().split("/");
        String code = parts.length > 0 ? parts[1] : invoice.getInvoiceLines()
                .get(0)
                .getCategories()
                .getAccountCode()
                .getAccountCode();

        String voucherNumber = documentSequenceService.generateVoucherNumber(schoolId, code);
        Payments paymentRequest = request.buildPayment();
        paymentRequest.setVoucherNumber(voucherNumber);
        Payments savedPayment = paymentRepository.save(paymentRequest);

        return PaymentResponse.builder()
                .paymentId(savedPayment.getId())
                .voucherNumber(voucherNumber)
                .paymentGatewayUrl("")
                .transactionReference(savedPayment.getTransactionId())
                .message("Payment created successfully")
                .build();
    }

    private List<PaymentRequest> getAllGuardianPayments(String guardianId, String academicYear) {
        return paymentRepository.findPaymentsByGuardianIdAndAcademicYear(
                UUID.fromString(guardianId), UUID.fromString(academicYear)
        );
    }
}
