package com.mg.course_service.mapper;

import com.mg.course_service.dto.VideoDTO;
import com.mg.course_service.model.Video;

public class VideoDTOConverter {
    public static VideoDTO toDTO(Video video) {
        return new VideoDTO(video.getId(), video.getDisplayName(), video.getFilename());
    }
}
