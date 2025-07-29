package com.mg.course_service.service;

import com.mg.course_service.dto.request.video.AddVideoToCourseRequest;
import com.mg.course_service.exception.VideoNotFoundException;
import com.mg.course_service.model.Course;
import com.mg.course_service.model.Video;
import com.mg.course_service.repository.VideoRepository;
import com.mg.course_service.validator.CourseValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.mg.course_service.config.RabbitMQConfig.ADD_VIDEO_QUEUE;
import static com.mg.course_service.config.RabbitMQConfig.DELETE_VIDEO_QUEUE;


@Service
public class VideoService {
    private final VideoRepository videoRepository;
    private final CourseService courseService;
    private final Logger logger = LoggerFactory.getLogger(VideoService.class);


    public VideoService(VideoRepository videoRepository,
                        CourseService courseService) {
        this.videoRepository = videoRepository;
        this.courseService = courseService;
    }

    //TODO: HAVE TO TRY
    @Transactional
    @RabbitListener(queues = ADD_VIDEO_QUEUE)
    public void addVideoToCourse(AddVideoToCourseRequest request) {
        Course course = courseService.findCourseById(request.courseId());
        CourseValidator.validateInstructorItSelfOrAdmin(course, request.token());

        Video video = new Video(request.filename(), request.displayName(), course);
        course.getCourseVideos().add(video);
        videoRepository.save(video);
    }

    @RabbitListener(queues = DELETE_VIDEO_QUEUE)
    public void deleteVideoFromCourse(String filename) {
        logger.info("Video silme mesajı alındı: {}", filename);

        Video video = videoRepository.getVideoByFilename(filename).orElseThrow(() ->
                new VideoNotFoundException("Video not found"));

        videoRepository.delete(video);
    }
}
