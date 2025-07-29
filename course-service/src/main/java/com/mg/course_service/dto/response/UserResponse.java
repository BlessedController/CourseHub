package com.mg.course_service.dto.response;

import java.time.LocalDate;
import java.util.UUID;

//TODO: GENDER AND ROLE THERE IS NOT HERE. BE SURE ABOUT THAT, THIS IS WORKING.
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
