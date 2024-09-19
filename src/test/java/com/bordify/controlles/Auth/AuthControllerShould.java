package com.bordify.controlles.Auth;

import com.bordify.controllers.auth.LoginRequest;
import com.bordify.models.User;
import com.bordify.repositories.UserRepository;
import com.bordify.shared.infrastucture.controlles.IntegrationControllersTestBaseClass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MvcResult;

import static com.bordify.shared.domain.FactoryValues.generateRandomAlphanumeric;
import static com.bordify.utils.GeneratorValuesRandom.generateRandomValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


public class AuthControllerShould extends IntegrationControllersTestBaseClass {

    @Autowired
    UserRepository userRepository;

    String urlLoginUser = "/auth/login/";

    @DisplayName("should make login")
    @Test
    public void shouldMakeLogin() throws Exception {

        User userExisted = createRandomPersistentUser();
        LoginRequest requestUserBody = LoginRequest.builder()
                .username(userExisted.getUsername())
                .password(userExisted.getPassword())
                .build();

        int isSuccesfull = 200;
        MvcResult result = assertRequestWithBody(HttpMethod.POST, urlLoginUser, requestUserBody, isSuccesfull,false)
//                .andExpect(status().isCreated())
//                .andExpect(header().exists("Authorization"))
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.username").value(userExisted.getUsername()))
                .andExpect(jsonPath("$.lastName").value(userExisted.getLastName()))
                .andExpect(jsonPath("$.phoneNumber").value(userExisted.getPhoneNumber()))
                .andReturn();

    }


    @DisplayName("shouldn't make login with wrong password")
    @Test
    public void shoulntdMakeLoginWithWrongPassword() throws Exception {

        User userExisted = createRandomPersistentUser();
        LoginRequest requestUserBody = LoginRequest.builder()
                .username(userExisted.getUsername())
                .password(generateRandomAlphanumeric(generateRandomValue(5,20)))
                .build();

        int isBadRequest = 400;
        MvcResult result = assertRequestWithBody(HttpMethod.POST, urlLoginUser, requestUserBody, isBadRequest,false)
//                .andExpect(status().isCreated())
//                .andExpect(header().exists("Authorization"))
                .andExpect(jsonPath("$.accessToken").doesNotExist())
                .andExpect(jsonPath("$.token").doesNotExist())
                .andExpect(jsonPath("$.username").doesNotExist())
                .andExpect(jsonPath("$.lastName").doesNotExist())
                .andExpect(jsonPath("$.phoneNumber").doesNotExist())
                .andReturn();

    }

    @DisplayName("shouldn't make login user not registered")
    @Test
    public void shoulntdMakeLoginUserNotRegistered() throws Exception {

        LoginRequest requestUserBody = LoginRequest.builder()
                .username(generateRandomAlphanumeric(generateRandomValue(5,20)))
                .password(generateRandomAlphanumeric(generateRandomValue(5,20)))
                .build();

        int isBadRequest = 400;
        MvcResult result = assertRequestWithBody(HttpMethod.POST, urlLoginUser, requestUserBody, isBadRequest,false)
//                .andExpect(status().isCreated())
//                .andExpect(header().exists("Authorization"))
                .andExpect(jsonPath("$.accessToken").doesNotExist())
                .andExpect(jsonPath("$.token").doesNotExist())
                .andExpect(jsonPath("$.username").doesNotExist())
                .andExpect(jsonPath("$.lastName").doesNotExist())
                .andExpect(jsonPath("$.phoneNumber").doesNotExist())
                .andReturn();

    }


}
