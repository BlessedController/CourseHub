package com.mg.identity_service.dto.requests;

import com.mg.identity_service.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateUserEmailRequest(
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        @UniqueEmail
        String email
) {
}
