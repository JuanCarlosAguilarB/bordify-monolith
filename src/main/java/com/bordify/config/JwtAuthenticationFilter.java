package com.bordify.config;

import java.io.IOException;

import com.bordify.services.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Filter class responsible for JWT authentication.
 * Validates JWT tokens from incoming requests and sets the authentication context if valid.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final UserDetailsService userDetailsService;

    @Autowired
    private final JwtService jwtService;

    /**
     * Performs the JWT token validation and sets the authentication context if valid.
     *
     * @param request The HTTP servlet request.
     * @param response The HTTP servlet response.
     * @param filterChain The filter chain for the request.
     * @throws ServletException If an error occurs during the filter execution.
     * @throws IOException If an I/O error occurs during the filter execution.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String token = getTokenFromRequest(request);
        final String username;

        if (token == null) {
//            ApiResponseHelper.sendErrorResponse(response, HttpStatus.BAD_REQUEST, "Bad Request", "El token no puede ser nulo");
            filterChain.doFilter(request, response);
            return;

        }
        username = jwtService.getUsernameFromToken(token);
        if (username == null) {
            ApiResponseHelper.sendErrorResponse(response, HttpStatus.BAD_REQUEST, "Bad Request", "El token is not valid");
            return;
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtService.isValidToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                // invalid token
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }

        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extracts the JWT token from the request headers.
     *
     * @param request The HTTP servlet request.
     * @return The JWT token if found, null otherwise.
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}



