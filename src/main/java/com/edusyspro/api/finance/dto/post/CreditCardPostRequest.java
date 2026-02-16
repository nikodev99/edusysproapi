package com.edusyspro.api.finance.dto.post;

import com.edusyspro.api.annotations.CVV;
import com.edusyspro.api.annotations.CardExpiry;
import com.edusyspro.api.annotations.LuhnCard;
import com.edusyspro.api.model.Address;
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
public class CreditCardPostRequest {
    @NotBlank(message = "card Number is required")
    @LuhnCard(message = "card Number must be digits (13-19) and pass the Luhn check")
    private String cardNumber;

    @NotBlank(message = "card CVV is required")
    @CVV(message = "card CVV must be 3 or 4 digits")
    private String cardCVV;

    @NotBlank(message = "card Holder is required")
    @Size(max = 100, message = "cardHolder must be at most {max} characters")
    @Pattern(regexp = "^[\\p{L} .'\\-&]+$", message = "card Holder contains invalid characters")
    private String cardHolder;

    @NotBlank(message = "card Expiry date is required")
    @CardExpiry(message = "card Expiry must be a valid future expiry in MM/yy or MM/yyyy")
    private String cardExpiry;

    @NotNull(message = "amount Paid is required")
    @Digits(integer = 12, fraction = 2, message = "amount Paid must have at most 12 integer digits and up to 2 fractional digits")
    @DecimalMin(value = "0.01", message = "amount Paid must be at least {value}")
    private BigDecimal amountPaid;

    private Address address;
}
