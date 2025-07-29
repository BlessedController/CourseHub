package com.coursehub.media_stock_service.exception;

public class AuthorIsNotTheOwnerOfTheCourseOrIsNotAdminException extends RuntimeException {
    public AuthorIsNotTheOwnerOfTheCourseOrIsNotAdminException(String message) {
        super(message);
    }
}
