package com.edusyspro.api.finance;

import com.edusyspro.api.finance.entities.Invoice;
import com.edusyspro.api.finance.entities.Payments;
import com.edusyspro.api.finance.enums.PaymentGateway;
import com.edusyspro.api.finance.enums.PaymentMethod;
import com.edusyspro.api.finance.enums.PaymentStatus;
import com.edusyspro.api.finance.repos.PaymentRepository;
import com.edusyspro.api.model.EnrollmentEntity;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.utils.Datetime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class PaymentTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    public void testCreatePayment() {
        YearMonth yearMonth = YearMonth.of(2025, 10);
        List<Payments> payments = List.of(
                Payments.builder()
                        .student(getStudent(117L))
                        .voucherNumber("VCH-" + yearMonth.getMonth().getValue() + "-" + yearMonth.getYear() + "-0001")
                        .invoice(getInvoice(1L))
                        .paymentDate(Datetime.brazzavilleDatetime())
                        .paymentMethod(PaymentMethod.CASH)
                        .amountPaid(BigDecimal.valueOf(25010))
                        .currency("XAF")
                        .transactionId(UUID.randomUUID().toString())
                        .paymentGateway(null)
                        .status(PaymentStatus.COMPLETED)
                        .receiptNumber(UUID.randomUUID().toString())
                        .processedBy(getIndividual(233L))
                        .createdBy(getIndividual(419L))
                        .notes("Payment de la facture de frais scolaire de novembre 2025")
                        .build(),
                Payments.builder()
                        .student(getStudent(117L))
                        .voucherNumber("VCH-" + yearMonth.getMonth().getValue() + "-" + yearMonth.getYear() + "-0002")
                        .invoice(getInvoice(2L))
                        .paymentDate(Datetime.brazzavilleDatetime())
                        .paymentMethod(PaymentMethod.MOBILE_MONEY)
                        .amountPaid(BigDecimal.valueOf(25010))
                        .currency("XAF")
                        .transactionId(UUID.randomUUID().toString())
                        .paymentGateway(PaymentGateway.MTN_MOMO)
                        .status(PaymentStatus.COMPLETED)
                        .receiptNumber(UUID.randomUUID().toString())
                        .processedBy(getIndividual(233L))
                        .createdBy(getIndividual(233L))
                        .notes("Payment de la facture de frais scolaire de décembre 2025")
                        .build(),
                Payments.builder()
                        .student(getStudent(117L))
                        .voucherNumber("VCH-" + yearMonth.getMonth().getValue() + "-" + yearMonth.getYear() + "-0003")
                        .invoice(getInvoice(3L))
                        .paymentDate(Datetime.brazzavilleDatetime())
                        .paymentMethod(PaymentMethod.MOBILE_MONEY)
                        .amountPaid(BigDecimal.valueOf(25010))
                        .currency("XAF")
                        .transactionId(UUID.randomUUID().toString())
                        .paymentGateway(PaymentGateway.MTN_MOMO)
                        .status(PaymentStatus.COMPLETED)
                        .receiptNumber(UUID.randomUUID().toString())
                        .processedBy(getIndividual(233L))
                        .createdBy(getIndividual(233L))
                        .notes("Payment de la facture de frais scolaire de Janvier 2026")
                        .build(),
                Payments.builder()
                        .student(getStudent(117L))
                        .voucherNumber("VCH-" + yearMonth.getMonth().getValue() + "-" + yearMonth.getYear() + "-0004")
                        .invoice(getInvoice(9L))
                        .paymentDate(Datetime.brazzavilleDatetime())
                        .paymentMethod(PaymentMethod.CASH)
                        .amountPaid(BigDecimal.valueOf(10000))
                        .currency("XAF")
                        .transactionId(UUID.randomUUID().toString())
                        .paymentGateway(null)
                        .status(PaymentStatus.COMPLETED)
                        .receiptNumber(UUID.randomUUID().toString())
                        .processedBy(getIndividual(233L))
                        .createdBy(getIndividual(419L))
                        .notes("Payment de la facture de l'inscription")
                        .build()
        );

        List<Payments> savedPayments = paymentRepository.saveAll(payments);
        assertFalse(savedPayments.isEmpty());
    }

    @Test
    public void testCreateAnotherPayment() {
        YearMonth yearMonth = YearMonth.of(2025, 10);
        List<Payments> payments = List.of(
                Payments.builder()
                        .student(getStudent(113L))
                        .voucherNumber("VCH-" + yearMonth.getMonth().getValue() + "-" + yearMonth.getYear() + "-0005")
                        .invoice(getInvoice(20L))
                        .paymentDate(Datetime.brazzavilleDatetime())
                        .paymentMethod(PaymentMethod.MOBILE_MONEY)
                        .amountPaid(BigDecimal.valueOf(2010))
                        .currency("XAF")
                        .transactionId(UUID.randomUUID().toString())
                        .paymentGateway(PaymentGateway.MTN_MOMO)
                        .status(PaymentStatus.COMPLETED)
                        .receiptNumber(UUID.randomUUID().toString())
                        .processedBy(getIndividual(225L))
                        .createdBy(getIndividual(225L))
                        .notes("Payment de la facture de frais scolaire de octobre 2025")
                        .build(),
                Payments.builder()
                        .student(getStudent(113L))
                        .voucherNumber("VCH-" + yearMonth.getMonth().getValue() + "-" + yearMonth.getYear() + "-0006")
                        .invoice(getInvoice(21L))
                        .paymentDate(Datetime.brazzavilleDatetime())
                        .paymentMethod(PaymentMethod.MOBILE_MONEY)
                        .amountPaid(BigDecimal.valueOf(2010))
                        .currency("XAF")
                        .transactionId(UUID.randomUUID().toString())
                        .paymentGateway(PaymentGateway.MTN_MOMO)
                        .status(PaymentStatus.COMPLETED)
                        .receiptNumber(UUID.randomUUID().toString())
                        .processedBy(getIndividual(225L))
                        .createdBy(getIndividual(225L))
                        .notes("Payment de la facture de frais scolaire de novembre 2025")
                        .build(),
                Payments.builder()
                        .student(getStudent(113L))
                        .voucherNumber("VCH-" + yearMonth.getMonth().getValue() + "-" + yearMonth.getYear() + "-0007")
                        .invoice(getInvoice(29L))
                        .paymentDate(Datetime.brazzavilleDatetime())
                        .paymentMethod(PaymentMethod.CASH)
                        .amountPaid(BigDecimal.valueOf(10000))
                        .currency("XAF")
                        .transactionId(UUID.randomUUID().toString())
                        .paymentGateway(null)
                        .status(PaymentStatus.COMPLETED)
                        .receiptNumber(UUID.randomUUID().toString())
                        .processedBy(getIndividual(225L))
                        .createdBy(getIndividual(419L))
                        .notes("Payment de la facture de l'inscription")
                        .build()
        );

        List<Payments> savedPayments = paymentRepository.saveAll(payments);
        assertFalse(savedPayments.isEmpty());
    }

    private EnrollmentEntity getStudent(Long studentId) {
        return EnrollmentEntity.builder().id(studentId).build();
    }
    private Invoice getInvoice(Long invoiceId) {
        return Invoice.builder().id(invoiceId).build();
    }
    private Individual getIndividual(Long individualId) {
        return Individual.builder().id(individualId).build();
    }

}
