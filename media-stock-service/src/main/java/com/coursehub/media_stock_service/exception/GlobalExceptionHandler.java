package com.coursehub.media_stock_service.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private Map<String, Object> createBody(HttpStatus status,
                                           String message,
                                           String path) {

        ZonedDateTime bakuTime = ZonedDateTime.now(ZoneId.of("Asia/Baku"));
        String formattedTime = bakuTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        Map<String, Object> body = new HashMap<>();
        body.put("status", status.value());
        body.put("message", message);
        body.put("path", path);
        body.put("timestamp", formattedTime);
        return body;

    }

    @ExceptionHandler(InvalidFileFormatException.class)
    public ResponseEntity<?> invalidFileFormatExceptionHandler(InvalidFileFormatException exception,
                                                               HttpServletRequest request) {
        Map<String, Object> body = createBody(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenPayloadParsingException.class)
    public ResponseEntity<?> tokenPayloadParsingExceptionHandler(TokenPayloadParsingException exception,
                                                                 HttpServletRequest request) {
        Map<String, Object> body = createBody(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<?> fileStorageExceptionHandler(FileStorageException exception,
                                                         HttpServletRequest request) {
        Map<String, Object> body = createBody(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorIsNotTheOwnerOfTheCourseOrIsNotAdminException.class)
    public ResponseEntity<?> authorIsNotTheOwnerOfTheCourseOrIsNotAdminExceptionHandler(
            AuthorIsNotTheOwnerOfTheCourseOrIsNotAdminException exception,
            HttpServletRequest request) {
        Map<String, Object> body = createBody(
                HttpStatus.UNAUTHORIZED,
                exception.getMessage(),
                request.getRequestURI());

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }
}
