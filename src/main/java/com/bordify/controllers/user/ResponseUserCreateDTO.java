package com.bordify.controllers.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseUserCreateDTO {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String accessToken;
    private String token;

}
