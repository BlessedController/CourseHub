package com.mg.course_service.exception;

public class CategoryMismatchException extends RuntimeException {
    public CategoryMismatchException(String message) {
        super(message);
    }
}
