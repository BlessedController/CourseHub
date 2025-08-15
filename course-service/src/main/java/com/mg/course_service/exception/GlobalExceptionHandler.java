package com.mg.course_service.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Map<String, Object> createErrorBody(HttpStatus status,
                                                String error,
                                                String message,
                                                String path) {

        ZonedDateTime bakuTime = ZonedDateTime.now(ZoneId.of("Asia/Baku"));
        String formattedTime = bakuTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("status", status.value());
        errorBody.put("error", error);
        errorBody.put("message", message);
        errorBody.put("path", path);
        errorBody.put("timestamp", formattedTime);

        return errorBody;
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<?> CourseNotFoundExceptionHandler(CourseNotFoundException exception,
                                                            HttpServletRequest request) {

        Map<String, Object> body = createErrorBody(
                HttpStatus.NOT_FOUND,
                "Not Found",
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> UserNotFoundExceptionHandler(UserNotFoundException exception,
                                                          HttpServletRequest request) {

        Map<String, Object> body = createErrorBody(
                HttpStatus.NOT_FOUND,
                "NotFound",
                exception.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(VideoNotFoundException.class)
    public ResponseEntity<?> VideoNotFoundExceptionHandler(VideoNotFoundException exception) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());

    }

    @ExceptionHandler(PhotoNotFoundException.class)
    public ResponseEntity<?> PhotoNotFoundExceptionHandler(PhotoNotFoundException exception) {
        HttpStatusCode code = HttpStatus.resolve(exception.getExceptionMessage().status());

        return new ResponseEntity<>(exception.getExceptionMessage(),
                Objects.requireNonNullElse(code, HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> CategoryLimitExceededExceptionHandler(CategoryLimitExceededException exception,
                                                                   HttpServletRequest request) {
        Map<String, Object> body = createErrorBody(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                exception.getMessage(),
                request.getRequestURI());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> HttpMessageNotReadableExceptionHandler(HttpMessageNotReadableException exception,
                                                                    HttpServletRequest request) {

        Map<String, Object> body = createErrorBody(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);

    }

    @ExceptionHandler(CategoryMismatchException.class)
    public ResponseEntity<?> categoryMismatchExceptionHandler(CategoryMismatchException exception,
                                                              HttpServletRequest request) {
        Map<String, Object> body = createErrorBody(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                exception.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(AuthorIsNotTheOwnerOfTheCourseOrIsNotAdminException.class)
    public ResponseEntity<?> authorIsNotTheOwnerOfTheCourseExceptionHandler(AuthorIsNotTheOwnerOfTheCourseOrIsNotAdminException exception,
                                                                            HttpServletRequest request) {
        Map<String, Object> body = createErrorBody(
                HttpStatus.FORBIDDEN,
                "Unauthorized access: instructor mismatch.",
                exception.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(TokenPayloadParsingException.class)
    public ResponseEntity<?> tokenPayloadParsingExceptionHandler(TokenPayloadParsingException exception,
                                                                 HttpServletRequest request) {
        Map<String, Object> body = createErrorBody(HttpStatus.BAD_REQUEST,
                "Bad Request",
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception,
                                                                    HttpServletRequest request) {
        Map<String, Object> body = createErrorBody(
                HttpStatus.NOT_ACCEPTABLE,
                "Not Acceptable",
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(body);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundExceptionHandler(ResourceNotFoundException exception,
                                                              HttpServletRequest request) {
        Map<String, Object> body = createErrorBody(
                HttpStatus.NOT_FOUND,
                "Media Not Found",
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(CourseAlreadyEnrolledException.class)
    public ResponseEntity<?> courseAlreadyEnrolledExceptionHandler(CourseAlreadyEnrolledException exception,
                                                                   HttpServletRequest request) {
        Map<String, Object> body = createErrorBody(
                HttpStatus.CONFLICT,
                "Already enrolled course",
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }


}
