package com.mg.identity_service.dto.requests;

import jakarta.validation.constraints.*;

public record UpdateUserPasswordRequest(
        @NotNull(message = "Password cannot be null")
        @NotBlank(message = "Password cannot be empty")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "Password must be at least 8 characters long and contain 1 uppercase letter, 1 lowercase letter, 1 number, and 1 special character"
        )
        String password
) {}
