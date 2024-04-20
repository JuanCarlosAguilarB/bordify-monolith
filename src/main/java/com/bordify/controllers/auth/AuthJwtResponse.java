package com.bordify.controllers.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthJwtResponse {

    private String token;
    private String refreshToken;

}
