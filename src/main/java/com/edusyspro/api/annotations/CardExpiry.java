package com.edusyspro.api.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CardExpiryValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CardExpiry {
    String message() default "invalid or expired card expiry";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
