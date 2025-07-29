package com.coursehub.media_stock_service.dto.response;

import org.springframework.core.io.Resource;

public record StreamResponse(
        Resource resource,
        long contentLength,
        long rangeStart,
        long rangeEnd,
        long filesize
) {
}
