package com.coursehub.media_stock_service.exception;

import java.io.IOException;

public class FileStorageException extends RuntimeException {
    public FileStorageException(String message) {
        super(message);
    }
}
