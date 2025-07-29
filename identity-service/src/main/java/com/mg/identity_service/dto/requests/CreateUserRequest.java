package com.mg.identity_service.dto.requests;

import com.mg.identity_service.model.Gender;
import com.mg.identity_service.validation.MinimumAge;
import jakarta.validation.constraints.*;

public record CreateUserRequest(

        @Pattern(regexp = "^[^\\\\/:*?\"<>|]+$", message = "Username cannot contain special characters: \\ / : * ? \" < > |")
        @NotNull(message = "Username must not be null")
        @NotBlank(message = "Username must not be blank")
        @Size(max = 30, message = "Username must not exceed 30 characters")
        String username,

        @NotNull(message = "Password must not be null")
        @NotBlank(message = "Password must not be blank")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
        )
        String password,

        @NotNull(message = "Phone number must not be null")
        @Pattern(
                regexp = "\\+994(50|51|55|70|77)\\d{7}",
                message = "Please provide a valid Azerbaijani phone number (e.g. +994501234567)"
        )
        String phoneNumber,

        @NotNull(message = "Email must not be null")
        @NotBlank(message = "Email must not be blank")
        @Email(message = "Invalid email format")
        String email,

        @NotNull(message = "First name must not be null")
        @Size(max = 50, message = "First name must not exceed 50 characters")
        String firstName,

        @NotNull(message = "Last name must not be null")
        @Size(max = 50, message = "Last name must not exceed 50 characters")
        String lastName,

        @NotNull(message = "Overview must not be null")
        @Size(max = 70, message = "Overview must not exceed 70 characters")
        String overview,

        @NotNull(message = "Description must not be null")
        @Size(max = 2000, message = "Description must not exceed 2000 characters")
        String description,

        @NotNull(message = "Gender must not be null")
        Gender gender
) {
}
