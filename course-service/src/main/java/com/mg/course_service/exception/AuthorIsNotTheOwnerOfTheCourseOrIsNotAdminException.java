package com.mg.course_service.exception;

public class AuthorIsNotTheOwnerOfTheCourseOrIsNotAdminException extends RuntimeException {
    public AuthorIsNotTheOwnerOfTheCourseOrIsNotAdminException(String message) {
        super(message);
    }
}
