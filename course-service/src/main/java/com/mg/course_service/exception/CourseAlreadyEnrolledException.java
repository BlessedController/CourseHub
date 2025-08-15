package com.mg.course_service.exception;

public class CourseAlreadyEnrolledException extends RuntimeException {
    public CourseAlreadyEnrolledException(String message) {
        super(message);
    }
}
