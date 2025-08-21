package com.mg.course_service.service;

import com.mg.course_service.client.IdentityServiceClient;
import com.mg.course_service.dto.CategoryDTO;
import com.mg.course_service.dto.request.course.CreateCourseRequest;
import com.mg.course_service.dto.request.course.UpdateCourseRequest;
import com.mg.course_service.dto.response.CourseResponse;
import com.mg.course_service.dto.response.UserResponse;
import com.mg.course_service.exception.AuthorIsNotTheOwnerOfTheCourseOrIsNotAdminException;
import com.mg.course_service.exception.CategoryLimitExceededException;
import com.mg.course_service.exception.CategoryMismatchException;
import com.mg.course_service.exception.CourseNotFoundException;
import com.mg.course_service.mapper.CategoryDTOConverter;
import com.mg.course_service.mapper.VideoDTOConverter;
import com.mg.course_service.model.Category;
import com.mg.course_service.model.Course;
import com.mg.course_service.repository.CourseRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.mg.course_service.config.RabbitMQConfig.EXCHANGE_NAME;
import static com.mg.course_service.config.RabbitMQConfig.UPDATE_ROLE_ROUTING_KEY;

@Service
public class CourseService {

    public static final int MAX_CATEGORIES = 3;
    private final CourseRepository courseRepository;
    private final IdentityServiceClient identityServiceClient;
    private final AmqpTemplate rabbitTemplate;

    public CourseService(CourseRepository courseRepository,
                         IdentityServiceClient identityServiceClient,
                         AmqpTemplate rabbitTemplate
    ) {
        this.courseRepository = courseRepository;
        this.identityServiceClient = identityServiceClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    protected void validateInstructorItSelfOrAdmin(Course course, HttpServletRequest httpRequest) {
        UUID userId = (UUID) httpRequest.getAttribute("userId");
        UUID instructorId = course.getInstructorId();
        Boolean isAdmin = (Boolean) httpRequest.getAttribute("isAdmin");

        if (isAdmin != null && !isAdmin && !Objects.equals(userId, instructorId)) {
            throw new AuthorIsNotTheOwnerOfTheCourseOrIsNotAdminException(
                    "You are neither the course owner nor an admin"
            );
        }
    }

    protected void validateInstructorItSelfOrAdmin(Course course, String token) {
        throw new AuthorIsNotTheOwnerOfTheCourseOrIsNotAdminException(
                "You are neither the course owner nor an admin"
        );
    }


    protected void validationCourseCategoriesSize(List<CategoryDTO> categoryDTOs) {
        if (categoryDTOs.size() > MAX_CATEGORIES) {
            throw new CategoryLimitExceededException("A course can have only " + MAX_CATEGORIES + " categories and them subcategories");
        }
    }

    protected void validateCategories(List<CategoryDTO> dtos) {

        for (CategoryDTO dto : dtos) {
            var main = dto.mainCategory();
            var sub = dto.category();

            if (main == null) {
                continue;
            }

            if (sub == null) {
                continue;
            }

            if (sub.getCategoryGroup() == null) {
                continue;
            }

            if (!sub.getCategoryGroup().equals(main)) {
                throw new CategoryMismatchException(String.format(
                        "The selected subcategory %s does not belong to the main category %s",
                        sub.getDisplayName(),
                        main.getDisplayName()
                ));
            }
        }
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
    public UUID createCourse(CreateCourseRequest request, HttpServletRequest httpRequest) {

        validationCourseCategoriesSize(request.categories());
        validateCategories(request.categories());

        UUID instructorId = (UUID) httpRequest.getAttribute("userId");

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
    public void updateCourseByCourseId(UUID courseId, UpdateCourseRequest request, HttpServletRequest httpRequest) {
        Course course = findCourseById(courseId);

        UUID userId = (UUID) httpRequest.getAttribute("userId");

        if (userId == null) {
            throw new AuthorIsNotTheOwnerOfTheCourseOrIsNotAdminException("You are not allowed to update this course");
        }

        validateInstructorItSelfOrAdmin(course, httpRequest);
        validationCourseCategoriesSize(request.categories());
        validateCategories(request.categories());

        Set<Category> newCategories = request.categories()
                .stream()
                .map(CategoryDTOConverter::toEntity)
                .collect(Collectors.toSet());

        course.getCategories().clear();
        course.getCategories().addAll(newCategories);

        course.setTitle(request.title());
        course.setDescription(request.description());
        course.setPrice(request.price());
        course.setPublished(request.isPublished());
    }


    public void deleteCourseById(UUID courseId, HttpServletRequest httpRequest) {
        Course course = findCourseById(courseId);

        validateInstructorItSelfOrAdmin(course, httpRequest);

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


    public List<CourseResponse> getAuthorCourses(HttpServletRequest httpRequest) {
        UUID instructorId = (UUID) httpRequest.getAttribute("userd");

        List<Course> courses = courseRepository.getCoursesByInstructorId(instructorId).orElseThrow(
                () -> new CourseNotFoundException(
                        "Course not found by instructor id: " + instructorId
                )
        );

        return courses.stream()
                .map(this::getCourseResponse)
                .toList();

    }

    public Boolean isUserOwnerOfCourse(UUID courseId, HttpServletRequest httpRequest) {
        Course course = findCourseById(courseId);
        UUID userId = (UUID) httpRequest.getAttribute("userId");
        UUID instructorId = course.getInstructorId();
        Boolean isAdmin = (Boolean) httpRequest.getAttribute("isAdmin");

        return Boolean.TRUE.equals(isAdmin) || Objects.equals(userId, instructorId);
    }
}
