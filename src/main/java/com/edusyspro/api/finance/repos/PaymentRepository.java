package com.edusyspro.api.finance.repos;

import com.edusyspro.api.finance.dto.request.PaymentRequest;
import com.edusyspro.api.finance.entities.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payments, UUID> {
    @Query("""
        SELECT new com.edusyspro.api.finance.dto.request.PaymentRequest(p.id, e.id, s.id, ind.firstName, ind.lastName, ind.reference,
        ind.image, e.classe.name, gp.firstName, gp.lastName, gp.reference, i.id, i.invoiceDate, i.dueDate, i.invoiceNumber,
        i.discount, i.taxAmount, i.totalAmount, p.amountPaid, p.currency, p.transactionId, p.voucherNumber, p.paymentDate,
        p.paymentGateway, p.paymentMethod, p.status, p.notes, pro.id, pro.firstName, pro.lastName, pro.reference, pro.image,
        c.id, c.firstName, c.lastName, c.reference, c.image) FROM Payments p JOIN p.student e JOIN p.invoice i JOIN e.student s
        JOIN s.guardian g JOIN s.personalInfo ind JOIN g.personalInfo gp JOIN p.processedBy pro JOIN p.createdBy c WHERE s.guardian.id = ?1
        AND e.academicYear.id = ?2 ORDER BY p.paymentDate DESC
    """)
    List<PaymentRequest> findPaymentsByGuardianIdAndAcademicYear(UUID guardianId, UUID academicYear);

    @Query("""
    SELECT new com.edusyspro.api.finance.dto.request.PaymentRequest(p.id, e.id, s.id, ind.firstName, ind.lastName, ind.reference,
    ind.image, e.classe.name, gp.firstName, gp.lastName, gp.reference, i.id, i.invoiceDate, i.dueDate, i.invoiceNumber,
    i.discount, i.taxAmount, i.totalAmount, p.amountPaid, p.currency, p.transactionId, p.voucherNumber, p.paymentDate,
    p.paymentGateway, p.paymentMethod, p.status, p.notes, pro.id, pro.firstName, pro.lastName, pro.reference, pro.image,
    c.id, c.firstName, c.lastName, c.reference, c.image) FROM Payments p JOIN p.student e JOIN p.invoice i JOIN e.student s
    JOIN s.guardian g JOIN s.personalInfo ind JOIN g.personalInfo gp JOIN p.processedBy pro JOIN p.createdBy c WHERE p.id = :paymentId
    """)
    Optional<PaymentRequest> findPaymentById(@Param("paymentId") UUID paymentId);
}
