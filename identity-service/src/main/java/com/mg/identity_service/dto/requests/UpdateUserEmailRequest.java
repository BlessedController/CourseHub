package com.mg.identity_service.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateUserEmailRequest(

        @NotNull(message = "Email cannot be null")
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        String email
) {
}
