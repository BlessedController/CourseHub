package com.mg.course_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PhotoNotFoundException extends RuntimeException {
    ExceptionMessage exceptionMessage;

    public PhotoNotFoundException(ExceptionMessage exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public ExceptionMessage getExceptionMessage() {
        return exceptionMessage;
    }
}
