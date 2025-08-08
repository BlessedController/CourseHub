package com.mg.course_service.dto.response;

import com.mg.course_service.enums.Gender;
import com.mg.course_service.enums.Role;

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
        String description,
        Gender gender,
        Role role
) {
}
