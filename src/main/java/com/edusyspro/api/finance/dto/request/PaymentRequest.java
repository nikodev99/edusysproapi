package com.edusyspro.api.finance.dto.request;

import com.edusyspro.api.dto.ClasseDTO;
import com.edusyspro.api.dto.EnrollmentDTO;
import com.edusyspro.api.dto.GuardianDTO;
import com.edusyspro.api.dto.StudentDTO;
import com.edusyspro.api.dto.custom.IndividualBasic;
import com.edusyspro.api.finance.dto.InvoiceDTO;
import com.edusyspro.api.finance.dto.PaymentHistory;
import com.edusyspro.api.finance.enums.PaymentGateway;
import com.edusyspro.api.finance.enums.PaymentMethod;
import com.edusyspro.api.finance.enums.PaymentStatus;
import com.edusyspro.api.model.Individual;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

public record PaymentRequest(
        UUID id,
        Long enrollmentId,
        UUID studentId,
        String studentFirstName,
        String studentLastName,
        String studentReference,
        String studentImage,
        String studentClasse,
        String guardianFirstName,
        String guardianLastName,
        String guardianReference,
        Long invoiceId,
        ZonedDateTime invoiceDate,
        LocalDate dueDate,
        String invoiceNumber,
        BigDecimal discount,
        BigDecimal taxAmount,
        BigDecimal totalAmount,
        BigDecimal amountPaid,
        String currency,
        String transactionId,
        String voucherNumber,
        ZonedDateTime paymentDate,
        PaymentGateway paymentGateway,
        PaymentMethod paymentMethod,
        PaymentStatus status,
        String notes,
        Long processedById,
        String processedByFirstName,
        String processedByLastName,
        String processedByReference,
        String processedByImage,
        Long createdById,
        String createdByFirstName,
        String createdByLastName,
        String createdByReference,
        String createdByImage
) 
{
    public PaymentHistory toHistory() {
        return PaymentHistory.builder()
                .paymentId(id)
                .enrolledStudent(EnrollmentDTO.builder()
                        .id(enrollmentId)
                        .student(StudentDTO.builder()
                                .id(studentId)
                                .personalInfo(Individual.builder()
                                        .firstName(studentFirstName)
                                        .lastName(studentLastName)
                                        .reference(studentReference)
                                        .image(studentImage)
                                        .build())
                                .build())
                        .classe(ClasseDTO.builder()
                                .name(studentClasse)
                                .build())
                        .build())
                .guardian(GuardianDTO.builder()
                        .personalInfo(Individual.builder()
                                .firstName(guardianFirstName)
                                .lastName(guardianLastName)
                                .reference(guardianReference)
                                .build())
                        .build())
                .invoice(InvoiceDTO.builder()
                        .invoiceId(invoiceId)
                        .dueDate(dueDate)
                        .invoiceDate(invoiceDate)
                        .invoiceNumber(invoiceNumber)
                        .discount(discount)
                        .taxAmount(taxAmount)
                        .totalAmount(totalAmount)
                        .build())
                .amountPaid(amountPaid)
                .voucherNumber(voucherNumber)
                .paymentDate(paymentDate)
                .currency(currency)
                .transactionId(transactionId)
                .paymentGateway(paymentGateway)
                .paymentMethod(paymentMethod)
                .status(status)
                .notes(notes)
                .processedBy(
                        new IndividualBasic(
                                processedById,
                                processedByFirstName,
                                processedByLastName,
                                processedByReference,
                                processedByImage
                        )
                )
                .createBy(
                        new IndividualBasic(
                                createdById,
                                createdByFirstName,
                                createdByLastName,
                                createdByReference,
                                createdByImage
                        )
                )
                .build();
    }
}
