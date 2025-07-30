package com.mg.course_service.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        String phoneNumber,
        String email,
        String firstName,
        String lastName,
        LocalDate birthDate,
        String overview,
        String description) {
}
