package com.bordify.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Helper class for sending error responses in JSON format.
 */
public class ApiResponseHelper {

    /**
     * Sends an error response with the specified status, error type, and message.
     *
     * @param response The HttpServletResponse object for sending the response.
     * @param status The HttpStatus status code for the response.
     * @param error The type of error that occurred.
     * @param message The error message describing the issue.
     * @throws IOException If an I/O error occurs during response writing.
     */
    public static void sendErrorResponse(HttpServletResponse response, HttpStatus status, String error, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");

        String jsonResponse = String.format("{\"timestamp\": \"%s\", \"status\": \"%d\", \"error\": \"%s\", \"message\": \"%s\"}",
                LocalDateTime.now(), status.value(), error, message);

        response.getWriter().write(jsonResponse);
    }
}