package com.edusyspro.api.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Currency;

public class CurrencyValidator implements ConstraintValidator<ValidCurrency, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value==null || value.isEmpty()) return true;

        try {
            Currency.getInstance(value.trim().toUpperCase());
            return true;
        }catch (IllegalArgumentException e) {
            return false;
        }
    }
}
