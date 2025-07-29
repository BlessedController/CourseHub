package com.mg.course_service.dto.request.video;

import java.util.UUID;

public record AddVideoToCourseRequest(
        String filename,
        String displayName,
        UUID courseId,
        String token
) {
}
