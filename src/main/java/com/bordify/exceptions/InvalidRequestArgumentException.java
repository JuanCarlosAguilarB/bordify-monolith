package com.bordify.exceptions;


/**
 * Exception class to represent invalid request arguments.
 */
public class InvalidRequestArgumentException extends RuntimeException {

    /**
     * Constructs a new InvalidRequestArgumentException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     */
    public InvalidRequestArgumentException(String message) {
        super(message);
    }

    /**
     * Constructs a new InvalidRequestArgumentException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   The cause (which is saved for later retrieval by the getCause() method).
     */
    public InvalidRequestArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}