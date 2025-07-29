package com.mg.identity_service.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUserUsernameRequest(
        @Pattern(regexp = "^[^\\\\/:*?\"<>|]+$", message = "Username cannot contain special characters: \\ / : * ? \" < > |")
        @NotNull(message = "Username cannot be null")
        @NotBlank(message = "Username cannot be empty")
        @Size(max = 30, message = "Username can include max 30 characters")
        String username
) {
}
