package com.edusyspro.api.finance.repos;

import com.edusyspro.api.finance.dto.InvoiceItemDto;
import com.edusyspro.api.finance.entities.InvoiceLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceLine, Long> {
    @Query("""
        SELECT new com.edusyspro.api.finance.dto.InvoiceItemDto(il.id, il.categories, il.description, il.quantity, il.unitPrice,
        il.discountPercentage, il.discountAmount, il.totalAmount) FROM InvoiceLine il WHERE il.invoice.id = ?1
    """)
    List<InvoiceItemDto> findInvoiceLinesByInvoiceId(Long invoiceId);
}
