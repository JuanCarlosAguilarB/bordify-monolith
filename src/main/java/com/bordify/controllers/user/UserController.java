package com.bordify.controllers.user;

import com.bordify.controllers.auth.ResponseDTO;
import com.bordify.models.User;
import com.bordify.repositories.UserRepository;
import com.bordify.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;




@Tag(name = "User", description = "User management operations")
@RestController
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userServices;

    @Operation(summary = "Create a new user", description = "Creates a new user", tags = { "User" })
    @PostMapping(value = "/users/")
    public ResponseEntity<?> createUser(@RequestBody RequestUserBody requestBody) {
        User user = User.builder()
                .username(requestBody.getUsername())
                .email(requestBody.getEmail())
                .firstName(requestBody.getFirstName())
                .lastName(requestBody.getLastName())
                .phoneNumber(requestBody.getPhoneNumber())
                .password(passwordEncoder.encode( requestBody.getPassword()))
                .build();


        String token = userServices.createUser(user).getToken();
        String id = user.getId().toString();

        ResponseUserCreateDTO responseDTO = ResponseUserCreateDTO.builder()
                .accessToken(token)
                .id(id)
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .token(token)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("Authorization", "Bearer " + token)
                .body(responseDTO);


    }


    @Autowired
    UserRepository userRepository;

    @Operation(summary = "Get a user", description = "Get a user", tags = { "User" })
    @GetMapping(value = "/users/me/")
    public User getUser() {

        User user = userRepository.findByUsername("1");
        return user;
    }
}
