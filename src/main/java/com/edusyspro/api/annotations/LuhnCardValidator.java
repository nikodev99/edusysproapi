package com.edusyspro.api.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LuhnCardValidator implements ConstraintValidator<LuhnCard, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) return true;
        String digits = value.replaceAll("[^0-9]", "");
        if (digits.length() < 13 || digits.length() > 19) return false;
        return luhnCheck(digits);
    }

    private boolean luhnCheck(String cardNumber) {
        int sum = 0;
        boolean alt = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = cardNumber.charAt(i) - '0';
            if (alt) {
                n *= 2;
                if (n > 9) n -= 9;
            }
            sum += n;
            alt = !alt;
        }
        return sum % 10 == 0;
    }
}
