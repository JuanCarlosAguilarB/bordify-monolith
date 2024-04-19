package com.bordify.exceptions;
public class EntityNotFound extends RuntimeException{
    public EntityNotFound(String message) {
        super(message);
    }
}
