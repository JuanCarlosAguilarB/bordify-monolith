package com.bordify.exceptions;


/**
 * Exception thrown for JWT token-related errors.
 */
public class JwtTokenException extends RuntimeException {

    /**
     * Constructs a new JwtTokenException with the specified error message.
     *
     * @param message The detail message of the exception.
     */
    public JwtTokenException(String message) {
        super(message);
    }
}
