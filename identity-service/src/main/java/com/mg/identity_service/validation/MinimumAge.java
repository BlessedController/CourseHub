package com.mg.identity_service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinimumAgeValidator.class)
public @interface MinimumAge {
    String message() default "En az {value} yaşında olmalısınız";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int value();
}
