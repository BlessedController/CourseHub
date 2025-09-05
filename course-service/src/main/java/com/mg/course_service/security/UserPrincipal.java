package com.mg.course_service.security;


import java.util.UUID;

public record UserPrincipal(
        UUID userId,
        Boolean isAdmin
) {
}