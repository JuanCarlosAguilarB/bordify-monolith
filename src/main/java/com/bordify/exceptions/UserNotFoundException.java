package com.bordify.exceptions;


/**
 * Exception thrown when a user is not found in the system.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructs a new UserNotFoundException with the specified error message.
     *
     * @param message The detail message of the exception.
     */
    public UserNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new UserNotFoundException with the specified error message and cause.
     *
     * @param message The detail message of the exception.
     * @param cause The cause of the exception.
     */
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}