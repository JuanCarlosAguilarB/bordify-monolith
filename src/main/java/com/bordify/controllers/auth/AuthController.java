package com.bordify.controllers.auth;

import com.bordify.models.User;
import com.bordify.services.JwtService;
import com.bordify.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller for authentication operations.
 */
@RestController
@Tag(name = "Auth", description = "Authentication operations")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    /**
     * Authenticates a user and returns a JWT token.
     *
     * @param loginRequest The login request containing the username and password.
     * @return A ResponseEntity with the JWT token and user information.
     */
    @Operation(summary = "Authenticate a user", description = "Authenticates a user and returns a JWT token", tags = { "Auth" })
    @PostMapping("/auth/login/")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {


        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password
                )
        );


        User user = userService.getUserByUsername(username);
        String id = user.getId().toString();

        String token = jwtService.getAccessToken(username);
        String refreshToken = jwtService.getRefreshToken(username);

        // Build a response DTO containing the user information and tokens
        ResponseDTO responseDTO = ResponseDTO.builder()
                .accessToken(token)
                .id(id)
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .refreshToken(refreshToken)
                .token(token)
                .build();


        return ResponseEntity.ok(responseDTO);
    }
}