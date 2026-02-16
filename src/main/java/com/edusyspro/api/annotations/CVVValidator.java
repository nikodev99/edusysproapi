package com.edusyspro.api.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CVVValidator implements ConstraintValidator<CVV, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) return true;
        return value.matches("^\\d{3,4}$");
    }
}
