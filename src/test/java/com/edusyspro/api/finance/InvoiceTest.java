package com.edusyspro.api.finance;

import com.edusyspro.api.finance.entities.*;
import com.edusyspro.api.finance.enums.InvoiceStatus;
import com.edusyspro.api.finance.repos.InvoiceRepository;
import com.edusyspro.api.finance.services.DocumentSequenceService;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.utils.Datetime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class InvoiceTest {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private DocumentSequenceService documentSequenceService;

    @Test
    public void testCreateInvoice() {
        Invoice invoice = Invoice.builder()
                .feeAssessment(getFeeAssessment(5L))
                .invoiceDate(Datetime.brazzavilleDatetime())
                .invoiceNumber(documentSequenceService.generateInvoiceNumber(
                        getFeeCategories(5L).getAccountCode().getSchool().getId().toString(),
                        getFeeCategories(5L).getAccountCode().getAccountCode()
                ))
                .dueDate(LocalDate.now())
                .subTotalAmount(BigDecimal.valueOf(10000))
                .taxAmount(BigDecimal.ZERO)
                .totalAmount(BigDecimal.valueOf(10000))
                .discount(BigDecimal.ZERO)
                .amountPaid(BigDecimal.valueOf(10000))
                .status(InvoiceStatus.PAID)
                .balanceDue(BigDecimal.ZERO)
                .notes("Facture à payé pour l'inscription")
                .createdBy(Individual.builder()
                        .id(419L)
                        .build())
                .createdAt(Datetime.brazzavilleDatetime())
                .build();

        InvoiceLine invoiceLine = InvoiceLine.builder()
                .invoice(invoice)
                .description("Paiement de frais d'inscription")
                .quantity(Byte.valueOf("1"))
                .unitPrice(BigDecimal.valueOf(10000))
                .discountPercentage(0.0)
                .discountAmount(BigDecimal.ZERO)
                .totalAmount(BigDecimal.valueOf(10000))
                .categories(getFeeCategories(2L))
                .build();

        invoice.setInvoiceLines(List.of(invoiceLine));

        List<Invoice> invoices = createInvoice(
                5L,
                BigDecimal.valueOf(25000),
                3,
                0.0
        );

        invoices.add(invoice);

        List<Invoice> savedInvoices = invoiceRepository.saveAll(invoices);
        assertFalse(savedInvoices.isEmpty());
    }

    @Test
    public void testCreateAnotherInvoice() {
        Invoice invoice = Invoice.builder()
                .feeAssessment(getFeeAssessment(6L))
                .invoiceDate(Datetime.brazzavilleDatetime())
                .invoiceNumber("INV-REG-0002")
                .dueDate(LocalDate.now())
                .subTotalAmount(BigDecimal.valueOf(10000))
                .taxAmount(BigDecimal.ZERO)
                .totalAmount(BigDecimal.valueOf(10000))
                .discount(BigDecimal.ZERO)
                .amountPaid(BigDecimal.valueOf(10000))
                .status(InvoiceStatus.PAID)
                .balanceDue(BigDecimal.ZERO)
                .notes("Facture à payé pour l'inscription")
                .createdBy(Individual.builder()
                        .id(419L)
                        .build())
                .createdAt(Datetime.brazzavilleDatetime())
                .build();

        InvoiceLine invoiceLine = InvoiceLine.builder()
                .invoice(invoice)
                .description("Paiement de frais d'inscription")
                .quantity(Byte.valueOf("1"))
                .unitPrice(BigDecimal.valueOf(10000))
                .discountPercentage(0.0)
                .discountAmount(BigDecimal.ZERO)
                .totalAmount(BigDecimal.valueOf(10000))
                .categories(getFeeCategories(2L))
                .build();

        invoice.setInvoiceLines(List.of(invoiceLine));

        List<Invoice> invoices = createInvoice(
                6L,
                BigDecimal.valueOf(25000),
                2,
                0.2
        );

        invoices.add(invoice);

        List<Invoice> savedInvoices = invoiceRepository.saveAll(invoices);
        assertFalse(savedInvoices.isEmpty());
    }

    private List<Invoice> createInvoice(
            Long assessmentId,
            BigDecimal subTotalAmount,
            int numberOfMonthPaid,
            Double discountPercentage
    ) {
        List<Invoice> invoices = new ArrayList<>();
        YearMonth start = YearMonth.of(2025, Month.OCTOBER);
        YearMonth end   = YearMonth.of(2026, Month.JUNE);
        long installment = ChronoUnit.MONTHS.between(start, end) + 1;
        for (int i = 0; i < installment; i++) {
            BigDecimal total = subTotalAmount.add(BigDecimal.TEN);
            String code = start.plusMonths(i).format(DateTimeFormatter.ofPattern("MMyyyy"));
            String month = start.plusMonths(i).format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.FRENCH));
            ZonedDateTime currentDate = Datetime.brazzavilleDatetime();
            BigDecimal discountAmount = subTotalAmount.multiply(BigDecimal.valueOf(discountPercentage));

            Invoice invoice = Invoice.builder()
                    .feeAssessment(getFeeAssessment(assessmentId))
                    .invoiceDate(currentDate)
                    .invoiceNumber("INV-"+code+"0"+i)
                    .dueDate(currentDate.toLocalDate().plusDays(3))
                    .subTotalAmount(subTotalAmount)
                    .taxAmount(BigDecimal.TEN)
                    .totalAmount(total)
                    .discount(discountAmount)
                    .notes("Facture à payé pour " + month)
                    .createdBy(Individual.builder()
                            .id(419L)
                            .build())
                    .createdAt(currentDate)
                    .build();

            if (i < numberOfMonthPaid) {
                invoice.setAmountPaid(total);
                invoice.setBalanceDue(BigDecimal.ZERO);
                invoice.setStatus(InvoiceStatus.PAID);
            }else {
                invoice.setAmountPaid(BigDecimal.ZERO);
                invoice.setBalanceDue(total);
                invoice.setStatus(InvoiceStatus.DRAFT);
            }

            InvoiceLine invoiceLine = InvoiceLine.builder()
                    .invoice(invoice)
                    .description("Paiement de frais scolaire")
                    .quantity(Byte.valueOf("1"))
                    .unitPrice(subTotalAmount)
                    .discountPercentage(discountPercentage)
                    .discountAmount(discountAmount)
                    .totalAmount(subTotalAmount.subtract(discountAmount))
                    .categories(getFeeCategories(1L))
                    .build();

            invoice.setInvoiceLines(List.of(invoiceLine));

            invoices.add(invoice);
        }

        return invoices;
    }

    private FeeAssessment getFeeAssessment(Long assessmentId) {
        return FeeAssessment.builder().id(assessmentId).build();
    }

    private FeeCategories getFeeCategories(Long assessmentId) {
        return FeeCategories.builder().id(assessmentId).build();
    }

}
