package com.mg.course_service.controller;

import com.mg.course_service.dto.request.course.CreateCourseRequest;
import com.mg.course_service.dto.request.course.UpdateCourseRequest;
import com.mg.course_service.dto.response.CourseResponse;
import com.mg.course_service.service.CourseService;
import jakarta.servlet.http.HttpServletRequest;
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
                                             HttpServletRequest httpRequest) {
        UUID id = courseService.createCourse(request, httpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping(UPDATE_COURSE_URL)
    public ResponseEntity<Void> updateCourse(@PathVariable UUID courseId,
                                             @RequestBody UpdateCourseRequest request,
                                             HttpServletRequest httpRequest) {

        courseService.updateCourseByCourseId(courseId, request, httpRequest);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @DeleteMapping(DELETE_COURSE_URL)
    public ResponseEntity<Void> deleteCourseById(@NotNull @PathVariable("courseId") UUID courseId,
                                                 HttpServletRequest httpRequest) {
        courseService.deleteCourseById(courseId, httpRequest);
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
    public ResponseEntity<List<CourseResponse>> getUserCourses(HttpServletRequest httpRequest) {
        List<CourseResponse> courses = courseService.getAuthorCourses(httpRequest);
        return ResponseEntity.ok(courses);
    }

    @GetMapping(VALIDATE_OWNER_URL)
    public ResponseEntity<Boolean> isUserOwnerOfCourse(@RequestParam UUID courseId,
                                                       HttpServletRequest httpRequest) {
        Boolean isOwner = courseService.isUserOwnerOfCourse(courseId, httpRequest);
        return ResponseEntity.ok(isOwner);
    }
}
