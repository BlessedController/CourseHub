package com.mg.identity_service.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Map<String, Object> createErrorBody(HttpStatus status,
                                                String error,
                                                String message,
                                                String path) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("error", error);
        errorBody.put("message", message);
        errorBody.put("path", path);
        errorBody.put("status", status.value());

        ZonedDateTime bakuTime = ZonedDateTime.now(ZoneId.of("Asia/Baku"));
        String formattedTime = bakuTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        errorBody.put("timestamp", formattedTime);

        return errorBody;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException exception,
                                                         HttpServletRequest request) {
        Map<String, Object> body = createErrorBody(
                HttpStatus.NOT_FOUND,
                "Not Found",
                exception.getMessage(),
                request.getRequestURI());


        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameOrPasswordMismatchException.class)
    public ResponseEntity<?> handleUsernameOrPasswordMismatchException(UsernameOrPasswordMismatchException exception,
                                                                       HttpServletRequest request) {
        Map<String, Object> body = createErrorBody(HttpStatus.NOT_FOUND, "Not Found",
                exception.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SelfOrAdminOnlyException.class)
    public ResponseEntity<?> handleSelfOrAdminOnlyException(SelfOrAdminOnlyException exception,
                                                            HttpServletRequest request) {
        Map<String, Object> body = createErrorBody(HttpStatus.FORBIDDEN, "Forbidden",
                exception.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticatedUserNotFoundException.class)
    public ResponseEntity<?> handleAuthenticatedUserNotFoundException(AuthenticatedUserNotFoundException exception,
                                                                      HttpServletRequest request) {
        Map<String, Object> body = createErrorBody(HttpStatus.NOT_FOUND, "Not Found",
                exception.getMessage(), request.getRequestURI());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolation(DataIntegrityViolationException ex,
                                                          HttpServletRequest request) {
        String message = "A value already exists.";

        if (ex.getMessage().contains("USERNAME")) {
            message = "This username is already taken.";
        } else if (ex.getMessage().contains("EMAIL")) {
            message = "This email address is already in use.";
        } else if (ex.getMessage().contains("PHONE_NUMBER")) {
            message = "This phone number is already in use.";
        }

        Map<String, Object> body = createErrorBody(HttpStatus.CONFLICT, "Conflict", message, request.getRequestURI());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    //TODO: BUNUN OUTPUT'UNA BAX
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex,
                                                        HttpServletRequest request) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });

        Map<String, Object> body = createErrorBody(HttpStatus.BAD_REQUEST,
                "Bad Request",
                "Validation failed for one or more fields",
                request.getRequestURI());

        body.put("validationErrors", fieldErrors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateFieldException.class)
    public ResponseEntity<?> duplicateFieldExceptionHandler(DuplicateFieldException exception,
                                                            HttpServletRequest request) {
        Map<String, Object> body = createErrorBody(
                HttpStatus.CONFLICT,
                "Conflict",
                exception.getMessage(),
                request.getRequestURI());

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TokenPayloadParsingException.class)
    public ResponseEntity<?> tokenPayloadParsingExceptionHandler(TokenPayloadParsingException exception,
                                                                 HttpServletRequest request) {
        Map<String, Object> body = createErrorBody(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                exception.getMessage(),
                request.getRequestURI());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
