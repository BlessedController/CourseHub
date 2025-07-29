package com.mg.course_service.dto;

import com.mg.course_service.model.Course;

import java.util.UUID;

public record VideoDTO(
        UUID id,
        String displayName,
        String filename
) {
}
