package com.edusyspro.api.finance.dto.request;

import com.edusyspro.api.dto.EnrollmentDTO;
import com.edusyspro.api.dto.StudentDTO;
import com.edusyspro.api.dto.custom.IndividualBasic;
import com.edusyspro.api.finance.dto.InvoiceDTO;
import com.edusyspro.api.finance.enums.InvoiceStatus;
import com.edusyspro.api.model.Individual;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

public record InvoiceRequest(
        Long invoiceId,
        Long enrollmentId,
        UUID studentId,
        String studentFirstName,
        String studentLastName,
        String studentReference,
        String studentImage,
        ZonedDateTime invoiceDate,
        LocalDate dueDate,
        String invoiceNumber,
        BigDecimal subTotalAmount,
        BigDecimal discount,
        BigDecimal taxAmount,
        BigDecimal totalAmount,
        BigDecimal amountPaid,
        BigDecimal balanceDue,
        InvoiceStatus status,
        Long issueById,
        String issueByFirstName,
        String issueByLastName,
        String issueByReference,
        String issueByImage,
        ZonedDateTime createdAt
) {
    public InvoiceDTO toDto() {
        return InvoiceDTO.builder()
                .invoiceId(invoiceId)
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
                        .build())
                .invoiceDate(invoiceDate)
                .dueDate(dueDate)
                .invoiceNumber(invoiceNumber)
                .subTotalAmount(subTotalAmount)
                .discount(discount)
                .taxAmount(taxAmount)
                .totalAmount(totalAmount)
                .amountPaid(amountPaid)
                .balanceDue(balanceDue)
                .status(status)
                .isOverdue((status != InvoiceStatus.PAID) && (dueDate.isBefore(LocalDate.now())))
                .issueBy(new IndividualBasic(
                        issueById,
                        issueByFirstName,
                        issueByLastName,
                        issueByImage,
                        issueByReference
                ))
                .build();
    }
}
