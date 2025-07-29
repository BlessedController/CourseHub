package com.coursehub.media_stock_service.service;

import com.coursehub.media_stock_service.dto.response.StreamResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.coursehub.media_stock_service.util.MediaValidator.validateProperty;

@Service
public class VideoStreamService {
    private final Path baseLocation;

    public VideoStreamService(@Value("${base.storage.location}") Path baseLocation) {
        this.baseLocation = baseLocation;
    }

    public StreamResponse streamVideo(String rangeHeader,
                                      String instructorName,
                                      String courseId,
                                      String filename) {

        validateProperty(instructorName);
        validateProperty(courseId);

        String targetDir = "videos" +
                File.separator + instructorName +
                File.separator + courseId +
                File.separator + filename;

        Path videoPath = baseLocation.resolve(targetDir);

        if (!Files.exists(videoPath) || !Files.isRegularFile(videoPath)) {
            throw new RuntimeException("Video file not found: " + filename);
        }

        long fileSize = 0;
        try {
            fileSize = Files.size(videoPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        long rangeStart = 0;
        long rangeEnd = fileSize - 1;

        if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
            String[] ranges = rangeHeader.substring(6).split("-");
            rangeStart = Long.parseLong(ranges[0]);
            if (ranges.length > 1 && !ranges[1].isEmpty()) {
                rangeEnd = Long.parseLong(ranges[1]);
            }
        }

        if (rangeStart < 0 || rangeStart > rangeEnd || rangeEnd >= fileSize) {
            rangeStart = 0;
            rangeEnd = fileSize - 1;
        }

        long contentLength = rangeEnd - rangeStart + 1;

        InputStream inputStream;
        try {
            inputStream = Files.newInputStream(videoPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        long skipped = 0;
        try {
            skipped = inputStream.skip(rangeStart);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (skipped < rangeStart) {
            throw new RuntimeException("Could not skip to start position in video stream");
        }

        Resource resource = new InputStreamResource(inputStream);

        return new StreamResponse(resource, contentLength, rangeStart, rangeEnd, fileSize);
    }

}
