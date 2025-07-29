package com.mg.identity_service.exception;

public class UsernameOrPasswordMismatchException extends RuntimeException {
    public UsernameOrPasswordMismatchException(String message) {
        super(message);
    }
}
