package com.coursehub.media_stock_service.controller;

import com.coursehub.media_stock_service.service.PhotoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/media/photo")
public class PhotoController {
    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadPhotoFile(@RequestParam("file") MultipartFile file,
                                                @RequestHeader("Authorization") String token) {

        photoService.uploadPhotoFile(file, token);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-profile-photo")
    public ResponseEntity<Void> deletePhoto(@RequestHeader("Authorization") String token) {
        photoService.deletePhoto(token);
        return ResponseEntity.noContent().build();
    }

}
