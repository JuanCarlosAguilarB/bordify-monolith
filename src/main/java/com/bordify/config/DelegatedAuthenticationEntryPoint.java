package com.bordify.config;

import com.bordify.exceptions.UserNotFoundException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * Component class serving as the entry point for authentication failure handling.
 * Responsible for customizing the response when authentication fails.
 */
@Component("delegatedAuthenticationEntryPoint")
public class DelegatedAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;


    /**
     * Handles the authentication failure scenario by customizing the response.
     *
     * @param request The HTTP servlet request.
     * @param response The HTTP servlet response.
     * @param authException The authentication exception that occurred.
     * @throws IOException If an I/O error occurs during response writing.
     * @throws ServletException If an error occurs during response handling.
     */
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

//        resolver.resolveException(request, response, null, authException);

        ApiResponseHelper.sendErrorResponse(response, HttpStatus.BAD_REQUEST, "Bad Request", authException.getMessage());

    }
}
