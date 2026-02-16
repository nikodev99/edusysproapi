package com.edusyspro.api.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LuhnCardValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface LuhnCard {
    String message() default "invalid card number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
