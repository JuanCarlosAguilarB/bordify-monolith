package com.bordify.exceptions;


/**
 * Exception thrown when attempting to create a user with an email or username that already exists.
 */
public class DuplicateEmailException extends RuntimeException {

    /**
     * Constructs a new DuplicateEmailException with the specified error message.
     *
     * @param message The detail message of the exception.
     */
    public DuplicateEmailException(String message) {
        super(message);
    }

    /**
     * Constructs a new DuplicateEmailException with the specified error message and cause.
     *
     * @param message The detail message of the exception.
     * @param cause The cause of the exception.
     */
    public DuplicateEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
