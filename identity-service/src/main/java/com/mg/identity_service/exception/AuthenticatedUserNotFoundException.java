package com.mg.identity_service.exception;

public class AuthenticatedUserNotFoundException extends RuntimeException {
    public AuthenticatedUserNotFoundException(String message) {
        super(message);
    }
}
