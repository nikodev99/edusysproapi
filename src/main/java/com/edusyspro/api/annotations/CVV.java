package com.edusyspro.api.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CVVValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CVV {
    String message() default "Invalid CVV";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
