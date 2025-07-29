package com.mg.identity_service.dto.requests;

import com.mg.identity_service.model.Gender;
import com.mg.identity_service.validation.MinimumAge;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateUserInfoRequest(
        String firstName,
        String lastName,
        LocalDate birthDate,
        Gender gender
) {
}
