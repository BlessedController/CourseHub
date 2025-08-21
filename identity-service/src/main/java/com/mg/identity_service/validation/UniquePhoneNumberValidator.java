package com.mg.identity_service.validation;

import com.mg.identity_service.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniquePhoneNumberValidator implements ConstraintValidator<UniquePhoneNumber, String> {
    private final UserService userService;

    public UniquePhoneNumberValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !userService.existsByPhoneNumber(value);
    }

}
