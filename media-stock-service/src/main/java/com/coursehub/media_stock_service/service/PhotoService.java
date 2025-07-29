package com.coursehub.media_stock_service.service;

import com.coursehub.media_stock_service.dto.ProfilePhotoResponse;
import com.coursehub.media_stock_service.exception.InvalidFileFormatException;
import com.coursehub.media_stock_service.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.coursehub.media_stock_service.config.RabbitMQConfig.*;
import static com.coursehub.media_stock_service.util.MediaValidator.getValidPhotoExtension;
import static com.coursehub.media_stock_service.util.MediaValidator.validateProperty;

@Service
public class PhotoService {
    private final PhotoStorageService photoStorageService;
    private final JwtUtil jwtUtil;
    private final AmqpTemplate rabbitTemplate;
    private final Logger logger = LoggerFactory.getLogger(PhotoService.class);

    public PhotoService(PhotoStorageService photoStorageService, JwtUtil jwtUtil, AmqpTemplate rabbitTemplate) {
        this.photoStorageService = photoStorageService;
        this.jwtUtil = jwtUtil;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void uploadPhotoFile(MultipartFile file, String token) {

        if (file == null || file.isEmpty()) {
            throw new InvalidFileFormatException("File is empty. Please select a valid file to upload");
        }

        String extension = getValidPhotoExtension(file);

        String username = jwtUtil.getUsernameFromToken(token);

        String profilePhotoName = photoStorageService.storePhoto(file, username, extension);

        ProfilePhotoResponse response = new ProfilePhotoResponse(profilePhotoName, token);

        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ADD_PHOTO_ROUTING_KEY, response);
    }

    public void deletePhoto(String token) {

        String currentUsername = jwtUtil.getUsernameFromToken(token);

        validateProperty(currentUsername);

        photoStorageService.deletePhoto(currentUsername);

        rabbitTemplate.convertAndSend(EXCHANGE_NAME, DELETE_PHOTO_ROUTING_KEY, token);
        logger.info("Sending token: {}", token);
    }
}
