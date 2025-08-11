package com.mg.course_service.service;

import com.mg.course_service.client.IdentityServiceClient;

import com.mg.course_service.dto.request.course.CreateCourseRequest;
import com.mg.course_service.dto.request.course.UpdateCourseRequest;
import com.mg.course_service.dto.response.CourseResponse;
import com.mg.course_service.dto.response.UserResponse;
import com.mg.course_service.exception.*;
import com.mg.course_service.mapper.VideoDTOConverter;
import com.mg.course_service.model.Category;
import com.mg.course_service.model.Course;
import com.mg.course_service.repository.CourseRepository;
import com.mg.course_service.mapper.CategoryDTOConverter;
import com.mg.course_service.util.JwtUtil;
import com.mg.course_service.validator.CourseValidator;
import jakarta.transaction.Transactional;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.mg.course_service.config.RabbitMQConfig.*;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final IdentityServiceClient identityServiceClient;
    private final AmqpTemplate rabbitTemplate;
    private final JwtUtil jwtUtil;
    private final CourseValidator courseValidator;

    public CourseService(CourseRepository courseRepository,
                         IdentityServiceClient identityServiceClient,
                         AmqpTemplate rabbitTemplate, JwtUtil jwtUtil,
                         CourseValidator courseValidator) {
        this.courseRepository = courseRepository;
        this.identityServiceClient = identityServiceClient;
        this.rabbitTemplate = rabbitTemplate;
        this.jwtUtil = jwtUtil;
        this.courseValidator = courseValidator;
    }

    protected Course findCourseById(UUID id) {
        return courseRepository.findById(id).orElseThrow(() ->
                new CourseNotFoundException("Course not found by id: " + id));
    }

    protected CourseResponse getCourseResponse(Course course) {
        return new CourseResponse(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                findUserById(course),
                course.getPrice(),
                course.getCategories()
                        .stream()
                        .map(CategoryDTOConverter::toDTO)
                        .toList(),
                course.getCourseVideos()
                        .stream()
                        .map(VideoDTOConverter::toDTO).toList()
        );
    }

    protected UserResponse findUserById(Course course) {
        return identityServiceClient.getUserById(course.getInstructorId()).getBody();
    }


    @Transactional
    public UUID createCourse(CreateCourseRequest request, String token) {

        courseValidator.validationCourseCategoriesSize(request.categories());
        courseValidator.validateCategories(request.categories());

        UUID instructorId = jwtUtil.getUserIdFromToken(token);


        Course course = new Course.Builder()
                .title(request.title())
                .description(request.description())
                .instructorId(instructorId)
                .price(request.price())
                .isPublished(request.isPublished())
                .categories(request.categories().stream()
                        .map(CategoryDTOConverter::toEntity)
                        .collect(Collectors.toSet()))
                .build();


        courseRepository.save(course);

        rabbitTemplate.convertAndSend(EXCHANGE_NAME, UPDATE_ROLE_ROUTING_KEY, instructorId);

        return course.getId();
    }

    @Transactional
    public void updateCourseByCourseId(UUID id, UpdateCourseRequest request, String token) {
        Course course = findCourseById(id);

        courseValidator.validateInstructorItSelfOrAdmin(course, token);

        if (request.categories() != null && !request.categories().isEmpty()) {

            courseValidator.validationCourseCategoriesSize(request.categories());
            courseValidator.validateCategories(request.categories());

            Set<Category> newCategories = request.categories()
                    .stream()
                    .map(CategoryDTOConverter::toEntity)
                    .collect(Collectors.toSet());

            course.getCategories().clear();
            course.getCategories().addAll(newCategories);
        }

        if (request.title() != null) course.setTitle(request.title());
        if (request.description() != null) course.setDescription(request.description());
        if (request.price() != null) course.setPrice(request.price());
        if (request.isPublished() != null) course.setPublished(request.isPublished());
    }

    public void deleteCourseById(UUID courseId, String token) {
        Course course = findCourseById(courseId);

        courseValidator.validateInstructorItSelfOrAdmin(course, token);

        courseRepository.deleteById(courseId);
    }

    public List<CourseResponse> getAllCourses() {
        List<Course> allCourses = courseRepository.findAll();

        return allCourses
                .stream()
                .map(this::getCourseResponse)
                .toList();
    }

    public CourseResponse getCourseById(UUID id) {
        Course course = findCourseById(id);

        return getCourseResponse(course);
    }


    public List<CourseResponse> getAuthorCourses(String token) {
        UUID instructorId = jwtUtil.getUserIdFromToken(token);

        List<Course> courses = courseRepository.getCoursesByInstructorId(instructorId).orElseThrow(
                () -> new CourseNotFoundException(
                        "Course not found by instructor id: " + instructorId
                )
        );

        return courses.stream()
                .map(this::getCourseResponse)
                .toList();

    }

    public Boolean isUserOwnerOfCourse(UUID courseId, String token) {
        Course course = findCourseById(courseId);
        UUID userId = jwtUtil.getUserIdFromToken(token);
        UUID instructorId = course.getInstructorId();
        boolean isAdmin = jwtUtil.isAdmin(token);

        return isAdmin || Objects.equals(userId, instructorId);
    }
}
