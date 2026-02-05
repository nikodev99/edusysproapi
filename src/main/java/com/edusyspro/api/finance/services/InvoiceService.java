package com.edusyspro.api.finance.services;

import com.edusyspro.api.finance.dto.InvoiceDTO;
import com.edusyspro.api.finance.dto.InvoiceItemDto;
import com.edusyspro.api.finance.dto.request.InvoiceLineRequest;
import com.edusyspro.api.finance.repos.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public InvoiceDTO getInvoiceById(Long id) {
        return invoiceRepository.findInvoiceById(id)
                .map(inv -> {
                    InvoiceDTO invoice = inv.toDto();
                    List<InvoiceItemDto> invoiceLines = invoiceRepository.findInvoiceLineByInvoice(inv.invoiceId()).stream()
                            .map(InvoiceLineRequest::toDto)
                            .toList();
                    invoice.setItems(invoiceLines);

                    return invoice;
                })
                .orElse(InvoiceDTO.builder().build());
    }
}
