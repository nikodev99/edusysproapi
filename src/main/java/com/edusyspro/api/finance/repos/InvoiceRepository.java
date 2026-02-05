package com.edusyspro.api.finance.repos;

import com.edusyspro.api.finance.dto.InvoiceDTO;
import com.edusyspro.api.finance.dto.request.InvoiceLineRequest;
import com.edusyspro.api.finance.dto.request.InvoiceRequest;
import com.edusyspro.api.finance.dto.request.OutstandingInvoice;
import com.edusyspro.api.finance.entities.Invoice;
import com.edusyspro.api.finance.enums.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Query("""
    SELECT new com.edusyspro.api.finance.dto.request.InvoiceRequest(i.id, s.id, st.id, p.firstName, p.lastName, p.reference, p.image,
        s.classe.name, gp.firstName, gp.lastName, gp.reference, i.invoiceDate, i.dueDate, i.invoiceNumber, i.subTotalAmount,
        i.discount, i.taxAmount, i.totalAmount, i.amountPaid, i.balanceDue, i.status, ind.id, ind.firstName, ind.lastName,
        ind.reference, ind.image, i.notes, i.createdAt) FROM Invoice i JOIN i.createdBy ind JOIN i.feeAssessment f JOIN f.student s
        JOIN s.student st JOIN st.guardian g JOIN st.personalInfo p JOIN g.personalInfo gp WHERE i.id = :invoiceId
    """)
    Optional<InvoiceRequest> findInvoiceById(@Param("invoiceId") Long invoiceId);

    @Query("""
        SELECT new com.edusyspro.api.finance.dto.request.InvoiceRequest(i.id, s.id, st.id, p.firstName, p.lastName, p.reference, p.image,
        s.classe.name, gp.firstName, gp.lastName, gp.reference, i.invoiceDate, i.dueDate, i.invoiceNumber, i.subTotalAmount,
        i.discount, i.taxAmount, i.totalAmount, i.amountPaid, i.balanceDue, i.status, ind.id, ind.firstName, ind.lastName,
        ind.reference, ind.image, i.notes, i.createdAt) FROM Invoice i JOIN i.createdBy ind JOIN i.feeAssessment f JOIN f.student s
        JOIN s.student st JOIN st.guardian g JOIN st.personalInfo p JOIN g.personalInfo gp WHERE st.guardian.id = :guardianId
        AND s.academicYear.school.id = :schoolId
    """)
    List<InvoiceRequest> findAllByGuardian(@Param("guardianId") UUID guardianId, @Param("schoolId") UUID schoolId);

    @Query("""
        SELECT new com.edusyspro.api.finance.dto.request.InvoiceRequest(i.id, s.id, st.id, p.firstName, p.lastName, p.reference, p.image,
        s.classe.name, gp.firstName, gp.lastName, gp.reference, i.invoiceDate, i.dueDate, i.invoiceNumber, i.subTotalAmount,
        i.discount, i.taxAmount, i.totalAmount, i.amountPaid, i.balanceDue, i.status, ind.id, ind.firstName, ind.lastName,
        ind.reference, ind.image, i.notes, i.createdAt) FROM Invoice i JOIN i.createdBy ind JOIN i.feeAssessment f JOIN f.student s
        JOIN s.student st JOIN st.guardian g JOIN st.personalInfo p JOIN g.personalInfo gp WHERE st.guardian.id = ?1
        AND s.academicYear.id = ?2
    """)
    List<InvoiceRequest> findAllByGuardianByAcademicYear(UUID guardianId, UUID academicYearId);

    @Query("""
        SELECT new com.edusyspro.api.finance.dto.request.InvoiceRequest(i.id, s.id, st.id, p.firstName, p.lastName, p.reference, p.image,
        s.classe.name, gp.firstName, gp.lastName, gp.reference, i.invoiceDate, i.dueDate, i.invoiceNumber, i.subTotalAmount,
        i.discount, i.taxAmount, i.totalAmount, i.amountPaid, i.balanceDue, i.status, ind.id, ind.firstName, ind.lastName,
        ind.reference, ind.image, i.notes, i.createdAt) FROM Invoice i JOIN i.createdBy ind JOIN i.feeAssessment f JOIN f.student s
        JOIN s.student st JOIN st.guardian g JOIN st.personalInfo p JOIN g.personalInfo gp WHERE i.invoiceNumber = ?1
    """)
    Optional<InvoiceRequest> findInvoiceByInvoiceNumber(String voucherNumber);

    @Query("""
        SELECT DISTINCT new com.edusyspro.api.finance.dto.request.InvoiceRequest(i.id, s.id, st.id, p.firstName, p.lastName, p.reference, p.image,
        s.classe.name, gp.firstName, gp.lastName, gp.reference, i.invoiceDate, i.dueDate, i.invoiceNumber, i.subTotalAmount,
        i.discount, i.taxAmount, i.totalAmount, i.amountPaid, i.balanceDue, i.status, ind.id, ind.firstName, ind.lastName,
        ind.reference, ind.image, i.notes, i.createdAt) FROM Invoice i JOIN i.createdBy ind JOIN i.feeAssessment f JOIN f.student s
        JOIN s.student st JOIN st.guardian g JOIN st.personalInfo p JOIN g.personalInfo gp WHERE st.guardian.id = ?1
        AND s.academicYear.id = ?2 AND i.status NOT IN ?3 AND i.amountPaid = ?4 AND ((i.dueDate BETWEEN ?5 AND ?6) OR (i.dueDate < ?5))
    """)
    List<InvoiceRequest> findActiveInvoice(UUID guardianId, UUID academicYearId, List<InvoiceStatus> status, BigDecimal amountPaid, LocalDate startDate, LocalDate endDate);

    @Query("""
        SELECT new com.edusyspro.api.finance.dto.request.InvoiceLineRequest(il.id, il.description, il.categories.name,
        il.categories.category_code, il.quantity, il.unitPrice, il.discountPercentage, il.discountAmount, il.totalAmount)
        FROM Invoice i JOIN i.invoiceLines il WHERE i.id = ?1
    """)
    List<InvoiceLineRequest> findInvoiceLineByInvoice(Long invoiceId);

    @Modifying
    @Transactional
    @Query("UPDATE Invoice i SET i.balanceDue = ?2, i.status = ?3, i.amountPaid = ?4, i.notes = ?5 WHERE i.id = ?1")
    void makeInvoicePaid(Long invoiceId, BigDecimal balanceDue, InvoiceStatus status, BigDecimal amountPaid, BigDecimal notes);

    @Modifying
    @Transactional
    @Query("UPDATE Invoice i SET i.status = ?2 WHERE i.id = ?1")
    void chanceInvoiceStatus(Long invoiceId, InvoiceStatus status);

    @Query("""
        SELECT i.balanceDue as balanceDue, i.dueDate as dueDate, s.id as id, f.amountPaid as amountPaid FROM Invoice i JOIN i.feeAssessment f
        JOIN f.student e JOIN e.student s WHERE s.guardian.id = ?1 AND e.academicYear.id = ?2 AND i.status IN ?3
    """)
    List<OutstandingInvoice> findOutstandingInvoicesByGuardianId(UUID guardianId, UUID academicYear, List<InvoiceStatus> statuses);
}
