package com.mg.identity_service.dto.responses;

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
