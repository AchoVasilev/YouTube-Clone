package com.example.server.exceptions;

public class EntityNotFoundException extends IllegalStateException{
    public EntityNotFoundException(String message) {
        super(message);
    }
}
