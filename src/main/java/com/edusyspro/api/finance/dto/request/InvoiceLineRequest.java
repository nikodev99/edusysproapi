package com.edusyspro.api.finance.dto.request;

import com.edusyspro.api.finance.dto.InvoiceItemDto;
import com.edusyspro.api.finance.entities.AccountCharts;
import com.edusyspro.api.finance.entities.FeeCategories;

import java.math.BigDecimal;

public record InvoiceLineRequest(
        Long id,
        String description,
        String categoryName,
        String categoryCode,
        Byte quantity,
        BigDecimal unitPrice,
        Double discountPercentage,
        BigDecimal discountAmount,
        BigDecimal totalAmount
) {
    public InvoiceItemDto toDto() {
        return InvoiceItemDto.builder()
                .id(id)
                .description(description)
                .quantity(quantity != null ? quantity.intValue() : 1)
                .unitPrice(unitPrice)
                .discountPercentage(discountPercentage)
                .discountAmount(discountAmount)
                .totalAmount(totalAmount)
                .category(FeeCategories.builder()
                        .name(categoryName)
                        .category_code(categoryCode)
                        .build())
                .build();
    }
}
