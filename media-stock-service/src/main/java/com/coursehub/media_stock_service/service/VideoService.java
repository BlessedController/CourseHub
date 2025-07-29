package com.coursehub.media_stock_service.service;

import com.coursehub.media_stock_service.dto.request.AddVideoToCourseRequest;
import com.coursehub.media_stock_service.exception.InvalidFileFormatException;
import com.coursehub.media_stock_service.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

import static com.coursehub.media_stock_service.config.RabbitMQConfig.*;
import static com.coursehub.media_stock_service.util.MediaValidator.validateProperty;

@Service
public class VideoService {

    private final VideoStorageService videoStorageService;
    private final JwtUtil jwtUtil;
    private final RabbitTemplate rabbitTemplate;
    private final Logger logger = LoggerFactory.getLogger(VideoService.class);

    public VideoService(VideoStorageService videoStorageService, JwtUtil jwtUtil, RabbitTemplate rabbitTemplate) {
        this.videoStorageService = videoStorageService;
        this.jwtUtil = jwtUtil;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void uploadVideoFile(MultipartFile file,
                                String token,
                                UUID courseId,
                                String displayName) {

        validateProperty(courseId.toString());
        validateProperty(displayName);


        if (file == null || file.isEmpty()) {
            throw new InvalidFileFormatException("File is empty. Please select a valid file to upload");
        }

        String instructorName = jwtUtil.getUsernameFromToken(token);

        String subFolder = "videos" +
                File.separator + instructorName +
                File.separator + courseId;

        String filename = videoStorageService.storeVideo(file, subFolder);

        AddVideoToCourseRequest request = new AddVideoToCourseRequest(filename, displayName, courseId, token);

        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ADD_VIDEO_ROUTING_KEY, request);

    }

    public void deleteVideo(String token, UUID courseId, String filename) {
        String instructorName = jwtUtil.getUsernameFromToken(token);

        validateProperty(instructorName);
        validateProperty(courseId.toString());
        validateProperty(filename);

        videoStorageService.deleteVideo(instructorName, courseId.toString(), filename);

        rabbitTemplate.convertAndSend(EXCHANGE_NAME, DELETE_VIDEO_ROUTING_KEY, filename);
        logger.info("Video silme mesajı gönderildi: {}", filename);

    }

}
