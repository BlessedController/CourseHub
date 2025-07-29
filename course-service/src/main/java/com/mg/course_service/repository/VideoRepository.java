package com.mg.course_service.repository;

import com.mg.course_service.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VideoRepository extends JpaRepository<Video, UUID> {
    Optional<Video> getVideoByFilename(String filename);
}
