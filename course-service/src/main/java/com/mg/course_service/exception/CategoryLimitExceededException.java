package com.mg.course_service.exception;

public class CategoryLimitExceededException extends RuntimeException {
    public CategoryLimitExceededException(String message) {
        super(message);
    }
}
