package com.mg.course_service.exception;


public record ExceptionMessage(String timestamp,
                               int status,
                               String httpStatusAsString,
                               String message,
                               String path
) {
}

