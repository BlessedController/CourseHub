package com.mg.identity_service.exception;

public class DuplicateFieldException extends RuntimeException {
    public DuplicateFieldException(String message) {
        super(message);
    }
}
