package com.mg.course_service.controller;

import com.mg.course_service.dto.VideoDTO;
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

@RestController
@RequestMapping("/v1/course")
@Validated
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/create-course")
    public ResponseEntity<UUID> createCourse(@Valid @RequestBody CreateCourseRequest request,
                                             @RequestHeader("Authorization") String token) {
        UUID id = courseService.createCourse(request, token);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }


    @PutMapping("/author/update-course/{courseId}")
    public ResponseEntity<Void> updateCourseByCourseId(@NotNull @PathVariable("courseId") UUID courseId,
                                                       @RequestBody @Valid UpdateCourseRequest request,
                                                       @RequestHeader("Authorization") String token) {

        courseService.updateCourseByCourseId(courseId, request, token);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping("/author/delete-course/{courseId}")
    public ResponseEntity<Void> deleteCourseById(@NotNull @PathVariable("courseId") UUID courseId,
                                                 @RequestHeader("Authorization") String token) {
        courseService.deleteCourseById(courseId, token);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/all-courses")
    public ResponseEntity<List<CourseResponse>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }


    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }


    @GetMapping("/user-courses")
    public ResponseEntity<List<CourseResponse>> getUserCourses(@RequestHeader("Authorization") String token) {
        List<CourseResponse> courses = courseService.getUserCourses(token);

        return ResponseEntity.status(HttpStatus.OK).body(courses);
    }

}