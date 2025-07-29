package com.mg.course_service.dto.response;

import java.util.UUID;

public record UserInfoResponse(
        UUID id,
        String username,
        String firstName,
        String lastName,
        String email,
        String overview,
        String description
) {
}
