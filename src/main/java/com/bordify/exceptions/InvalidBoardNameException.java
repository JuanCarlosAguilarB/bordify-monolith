package com.bordify.exceptions;


/**
 * Exception thrown for JWT token-related errors.
 */
public class InvalidBoardNameException extends RuntimeException {

    /**
     * Constructs a new JwtTokenException with the specified error message.
     *
     * @param message The detail message of the exception.
     */
    public InvalidBoardNameException(String message) {
        super(message);
    }
}
