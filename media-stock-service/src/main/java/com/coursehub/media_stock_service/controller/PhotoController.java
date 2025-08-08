package com.coursehub.media_stock_service.controller;

import com.coursehub.media_stock_service.service.PhotoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.coursehub.media_stock_service.constants.PhotoApiUrls.*;

@RestController
@RequestMapping(PHOTO_API_BASE_URL)
public class PhotoController {
    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping(value = UPLOAD_PROFILE_PHOTO_URL, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadPhotoFile(@RequestParam("file") MultipartFile file,
                                                @RequestHeader("Authorization") String token) {

        photoService.uploadPhotoFile(file, token);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(DELETE_PROFILE_PHOTO_URL)
    public ResponseEntity<Void> deletePhoto(@RequestHeader("Authorization") String token) {
        photoService.deletePhoto(token);
        return ResponseEntity.noContent().build();
    }

}
