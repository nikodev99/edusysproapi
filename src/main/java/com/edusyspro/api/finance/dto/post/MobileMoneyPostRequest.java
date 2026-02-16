package com.edusyspro.api.finance.dto.post;

import com.edusyspro.api.annotations.ValidCurrency;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MobileMoneyPostRequest {
    @NotBlank(message = "phoneNumber is required")
    private String phoneNumber;

    @NotNull(message = "amountPaid is required")
    @DecimalMin(value = "0.01", message = "amountPaid must be at least {value}")
    @Digits(integer = 12, fraction = 2, message = "amountPaid must have at most 12 integer digits and 2 fractional digits")
    private BigDecimal amountPaid;

    @NotBlank(message = "currency is required")
    @Size(min = 3, max = 3, message = "currency must be a 3-letter ISO-4217 code")
    @ValidCurrency(message = "currency must be a valid ISO-4217 currency code (e.g. XAF, USD)")
    private String currency;

    @Size(max = 500, message = "notes must be at most {max} characters")
    @Pattern(regexp = "^\\P{Cntrl}*$", message = "notes must not contain control characters")
    @Column(length = 500)
    private String notes;
}
