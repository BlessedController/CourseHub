package com.mg.identity_service.dto.requests;

import com.mg.identity_service.validation.UniquePhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateUserPhoneNumberRequest(
        @NotBlank(message = "Phone Number cannot be blank")
        @Pattern(regexp = "\\+994(50|51|55|70|77)\\d{7}",
                message = "Please provide a valid Azerbaijani phone number (e.g. +994501234567)")
        @UniquePhoneNumber
        String phoneNumber

) {
}
