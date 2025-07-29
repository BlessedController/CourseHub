package com.coursehub.media_stock_service.dto.request;

import java.util.UUID;

public record AddVideoToCourseRequest(
        String filename,
        String displayName,
        UUID courseId,
        String token
) {
}