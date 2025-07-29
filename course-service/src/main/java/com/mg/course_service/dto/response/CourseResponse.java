package com.mg.course_service.dto.response;

import com.mg.course_service.dto.CategoryDTO;
import com.mg.course_service.dto.VideoDTO;

import java.util.List;
import java.util.UUID;

public record CourseResponse(
        UUID id,
        String title,
        String description,
        UserInfoResponse instructor,
        Double price,
        List<CategoryDTO> categories,
        List<VideoDTO> courseVideos
) {
}
