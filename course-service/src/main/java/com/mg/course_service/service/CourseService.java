package com.mg.course_service.service;

import com.mg.course_service.client.IdentityServiceClient;

import com.mg.course_service.dto.request.course.CreateCourseRequest;
import com.mg.course_service.dto.request.course.UpdateCourseRequest;
import com.mg.course_service.dto.response.CourseResponse;
import com.mg.course_service.dto.response.UserResponse;
import com.mg.course_service.exception.*;
import com.mg.course_service.mapper.UpdateCourseRequestConverter;
import com.mg.course_service.mapper.VideoDTOConverter;
import com.mg.course_service.model.Course;
import com.mg.course_service.repository.CourseRepository;
import com.mg.course_service.mapper.CategoryDTOConverter;
import com.mg.course_service.util.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.mg.course_service.validator.CourseValidator.*;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final IdentityServiceClient identityServiceClient;

    public CourseService(CourseRepository courseRepository,
                         IdentityServiceClient identityServiceClient) {
        this.courseRepository = courseRepository;
        this.identityServiceClient = identityServiceClient;
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
                findUserInfoById(course),
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

    protected UserResponse findUserInfoById(Course course) {
        return identityServiceClient.getUserById(course.getInstructorId()).getBody();
    }


    @Transactional
    public UUID createCourse(CreateCourseRequest request, String token) {

        validationCourseCategoriesSize(request.categories());
        validateCategories(request.categories());

        UUID instructorId = JwtUtil.getUserIdFromToken(token);

        Course course = new Course(
                request.title(),
                request.description(),
                instructorId,
                request.price(),
                request.isPublished(),
                request.categories()
                        .stream()
                        .map(CategoryDTOConverter::toEntity)
                        .collect(Collectors.toSet())
        );

        courseRepository.save(course);

        return course.getId();
    }

    @Transactional
    public void updateCourseByCourseId(UUID id, UpdateCourseRequest request, String token) {
        Course course = findCourseById(id);

        validateInstructorItSelfOrAdmin(course, token);

        UpdateCourseRequestConverter.updateEntity(request, course);
    }

    public void deleteCourseById(UUID courseId, String token) {
        Course course = findCourseById(courseId);

        validateInstructorItSelfOrAdmin(course, token);

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


    public List<CourseResponse> getUserCourses(String token) {
        UUID instructorId = JwtUtil.getUserIdFromToken(token);

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
        UUID userId = JwtUtil.getUserIdFromToken(token);
        UUID instructorId = course.getInstructorId();
        boolean isAdmin = JwtUtil.isAdmin(token);

        return isAdmin || Objects.equals(userId, instructorId);
    }
}
