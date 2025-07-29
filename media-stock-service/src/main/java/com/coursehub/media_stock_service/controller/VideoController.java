package com.coursehub.media_stock_service.controller;

import com.coursehub.media_stock_service.dto.response.StreamResponse;
import com.coursehub.media_stock_service.service.VideoService;
import com.coursehub.media_stock_service.service.VideoStreamService;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("v1/media/video")
public class VideoController {

    private final VideoService videoService;
    private final VideoStreamService videoStreamService;

    public VideoController(VideoService videoService, VideoStreamService videoStreamService) {
        this.videoService = videoService;
        this.videoStreamService = videoStreamService;
    }


    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadVideoFile(@RequestParam("file") MultipartFile file,
                                                @RequestHeader("Authorization") String token,
                                                @RequestParam("courseId") UUID courseId,
                                                @RequestParam("displayName") String displayName
    ) {

        videoService.uploadVideoFile(file, token, courseId, displayName);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/stream/{filename}")
    public ResponseEntity<Resource> streamVideo(
            @PathVariable String filename,
            @RequestHeader(value = "Range", required = false) String rangeHeader,
            String username,
            String courseId) {

        StreamResponse response = videoStreamService.streamVideo(filename, username, courseId, rangeHeader);

        return ResponseEntity.status(rangeHeader == null ? 200 : 206)
                .header("Content-Type", "video/mp4")
                .header("Accept-Ranges", "bytes")
                .header("Content-Length", String.valueOf(response.contentLength()))
                .header("Content-Range", "bytes " + response.rangeStart() + "-" + response.rangeEnd() + "/" + response.filesize())
                .header("Content-Disposition", "inline; filename=\"" + filename + "\"")
                .body(response.resource());
    }

    @DeleteMapping("/delete/{courseId}/{filename}")
    public ResponseEntity<Void> deleteVideo(@RequestHeader("Authorization") String token,
                                            @PathVariable UUID courseId,
                                            @PathVariable String filename) {

        videoService.deleteVideo(token, courseId, filename);
        return ResponseEntity.noContent().build();

    }

}