package com.mg.identity_service.exception;

public class SelfOrAdminOnlyException extends RuntimeException {
    public SelfOrAdminOnlyException(String message) {
        super(message);
    }
}
