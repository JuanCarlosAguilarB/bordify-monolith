package com.bordify.controllers.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDTO {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String accessToken;
    private String refreshToken;
    private String token;

}
