package com.mg.identity_service.dto.requests;

import com.mg.identity_service.model.Gender;
import com.mg.identity_service.validation.UniqueEmail;
import com.mg.identity_service.validation.UniquePhoneNumber;
import com.mg.identity_service.validation.UniqueUsername;
import jakarta.validation.constraints.*;

public record CreateUserRequest(

        @Pattern(regexp = "^[^\\\\/:*?\"<>|]+$", message = "Username cannot contain special characters: \\ / : * ? \" < > |")
        @NotBlank(message = "Username cannot be blank")
        @Size(max = 30, message = "Username must not exceed 30 characters")
        @UniqueUsername
        String username,

        @NotBlank(message = "Password cannot be blank")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
        )
        String password,

        @NotBlank(message = "Phone number cannot be blank")
        @Pattern(
                regexp = "\\+994(50|51|55|70|77)\\d{7}",
                message = "Please provide a valid Azerbaijani phone number (e.g. +994501234567)"
        )
        @UniquePhoneNumber
        String phoneNumber,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        @UniqueEmail
        String email,

        @NotBlank(message = "First name cannot be blank")
        @Size(max = 50, message = "First name must not exceed 50 characters")
        String firstName,

        @NotBlank(message = "Last name cannot be blank")
        @Size(max = 50, message = "Last name must not exceed 50 characters")
        String lastName,

        @NotBlank(message = "Overview cannot be blank")
        @Size(max = 70, message = "Overview must not exceed 70 characters")
        String overview,

        @NotBlank(message = "Description cannot be blank")
        @Size(max = 2000, message = "Description must not exceed 2000 characters")
        String description,

        @NotNull(message = "Gender cannot not be null")
        Gender gender
) {
}
