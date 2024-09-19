package com.bordify.controlles.user;

import com.bordify.controllers.user.RequestUserBody;
import com.bordify.models.User;
import com.bordify.repositories.UserRepository;
import com.bordify.shared.infrastucture.controlles.IntegrationControllersTestBaseClass;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.bordify.shared.domain.FactoryValues.*;
import static com.bordify.utils.GeneratorValuesRandom.generateRandomValue;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class UserControllerShould extends IntegrationControllersTestBaseClass {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    String urlCreationUser = "/users/";

    @DisplayName("should create a user")
    @Test
    public void testCreateUserAndRetrieveUser() throws Exception {

        RequestUserBody  requestUserBody = RequestUserBody.builder()
                .username(generateRandomAlphanumeric(generateRandomValue(5,20)))
                .password(generateRandomAlphanumeric(generateRandomValue(5,20)))
                .email(generateRandomEmail())
                .firstName(generateRandomString(generateRandomValue(5,20)))
                .lastName(generateRandomString(generateRandomValue(5,20)))
                .phoneNumber(generateRandomPhoneNumber())
                .build();



        MvcResult result = mockMvc.perform(post(urlCreationUser)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestUserBody))
                )
                .andExpect(status().isCreated())
                .andExpect(header().exists("Authorization"))
                .andExpect(jsonPath("$.username").value(requestUserBody.getUsername()))
                .andExpect(jsonPath("$.email").value(requestUserBody.getEmail()))
                .andExpect(jsonPath("$.firstName").value(requestUserBody.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(requestUserBody.getLastName()))
                .andExpect(jsonPath("$.phoneNumber").value(requestUserBody.getPhoneNumber()))
                .andReturn();

        // verify that user was created and stored in the db
        User userFounded = userRepository.findByUsername(requestUserBody.getUsername());

        Assertions.assertNotNull(userFounded);
        Assertions.assertEquals(userFounded.getUsername(),requestUserBody.getUsername());
        Assertions.assertEquals(userFounded.getEmail(),requestUserBody.getEmail());
        Assertions.assertEquals(userFounded.getFirstName(),requestUserBody.getFirstName());
        Assertions.assertEquals(userFounded.getLastName(),requestUserBody.getLastName());
        Assertions.assertEquals(userFounded.getPhoneNumber(),requestUserBody.getPhoneNumber());

    }

    @DisplayName("shouldn't create a user because user Exist")
    @Test
    public void shouldntCreateAnUserThatExist() throws Exception {

//        userRepository.deleteAll(); // clear // we dont clear db because if someone run test in db production,
        User userPersited = createRandomPersistentUser();
        userRepository.flush();
        assertTrue(userRepository.existsByUsername(userPersited.getUsername())); // verify that user exist

        RequestUserBody  requestUserBody = RequestUserBody.builder()
                .username(userPersited.getUsername())
                .password(generateRandomAlphanumeric(generateRandomValue(5,20)))
                .email(userPersited.getEmail())
                .firstName(userPersited.getFirstName())
                .lastName(userPersited.getLastName())
                .phoneNumber(userPersited.getPhoneNumber())
                .build();


        int isConflictStatus = 409 ;
        MvcResult result = assertRequest(HttpMethod.POST, urlCreationUser, isConflictStatus,false)
                .andReturn();

        // verify that only find one user.
        User userFounded = userRepository.findByUsername(requestUserBody.getUsername());

    }



}
