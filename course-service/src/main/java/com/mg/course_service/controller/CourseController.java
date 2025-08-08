package com.mg.course_service.controller;

import com.mg.course_service.dto.request.course.CreateCourseRequest;
import com.mg.course_service.dto.request.course.UpdateCourseRequest;
import com.mg.course_service.dto.response.CourseResponse;
import com.mg.course_service.service.CourseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.mg.course_service.constant.CourseApiUrls.*;

@RestController
@RequestMapping(COURSE_API_BASE_URL)
@Validated
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping(CREATE_COURSE_URL)
    public ResponseEntity<UUID> createCourse(@Valid @RequestBody CreateCourseRequest request,
                                             @RequestHeader("Authorization") String token) {
        UUID id = courseService.createCourse(request, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping(UPDATE_COURSE_URL)
    public ResponseEntity<Void> updateCourseByCourseId(@NotNull @PathVariable("courseId") UUID courseId,
                                                       @Valid @RequestBody UpdateCourseRequest request,
                                                       @RequestHeader("Authorization") String token) {
        courseService.updateCourseByCourseId(courseId, request, token);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(DELETE_COURSE_URL)
    public ResponseEntity<Void> deleteCourseById(@NotNull @PathVariable("courseId") UUID courseId,
                                                 @RequestHeader("Authorization") String token) {
        courseService.deleteCourseById(courseId, token);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(ALL_COURSES_URL)
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping(COURSE_BY_ID_URL)
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }

    @GetMapping(AUTHOR_COURSES_URL)
    public ResponseEntity<List<CourseResponse>> getUserCourses(@RequestHeader("Authorization") String token) {
        List<CourseResponse> courses = courseService.getAuthorCourses(token);
        return ResponseEntity.ok(courses);
    }

    @GetMapping(VALIDATE_OWNER_URL)
    public ResponseEntity<Boolean> isUserOwnerOfCourse(@RequestParam UUID courseId,
                                                       @RequestHeader("Authorization") String token) {
        Boolean isOwner = courseService.isUserOwnerOfCourse(courseId, token);
        return ResponseEntity.ok(isOwner);
    }
}
