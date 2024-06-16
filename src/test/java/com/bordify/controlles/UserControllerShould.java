package com.bordify.controlles;
import com.bordify.controllers.user.RequestUserBody;

import com.bordify.controllers.user.ResponseUserCreateDTO;
import com.bordify.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest()
@AutoConfigureMockMvc
public class UserControllerShould {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateUserAndRetrieveUser() throws Exception {

        RequestUserBody  requestUserBody = RequestUserBody.builder()
                .username("testuser")
                .email("testuser@example.com")
                .password("testpassword")
                .firstName("Test")
                .lastName("User")
                .phoneNumber("1234567890")
                .build();

        MvcResult result = mockMvc.perform(post("/users/")
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

    }
}
