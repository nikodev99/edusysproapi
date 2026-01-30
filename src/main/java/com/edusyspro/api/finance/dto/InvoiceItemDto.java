package com.edusyspro.api.finance.dto;

import com.edusyspro.api.finance.entities.FeeCategories;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceItemDto {
    private Long id;
    private FeeCategories category;
    private String description;
    private int quantity;
    private BigDecimal unitPrice;
    private Double discountPercentage;
    private BigDecimal discountAmount;
    private BigDecimal totalAmount;
}
