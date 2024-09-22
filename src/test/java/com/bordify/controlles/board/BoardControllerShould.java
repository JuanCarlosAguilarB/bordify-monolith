package com.bordify.controlles.board;

import com.bordify.controllers.board.BoardRequest;
import com.bordify.shared.infrastucture.controlles.IntegrationControllersTestBaseClass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MvcResult;

import static com.bordify.shared.domain.FactoryValues.generateRandomAlphanumeric;
import static com.bordify.utils.GeneratorValuesRandom.generateRandomValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class BoardControllerShould extends IntegrationControllersTestBaseClass {

    String urlBoardCration = "/boards/";

    @DisplayName("should create a board")
    @Test
    public void shouldCreateABoard() throws Exception {

        BoardRequest boardRequest = BoardRequest.builder()
                .name(generateRandomAlphanumeric(generateRandomValue(5,20)))
                .build();

        int wasCreated = 201;


        MvcResult result = assertRequestWithBody(HttpMethod.POST, urlBoardCration, boardRequest, wasCreated,true)
//                .andExpect(status().isCreated())
//                .andExpect(header().exists("Authorization"))
//                .andExpect(jsonPath("$.accessToken").exists())
//                .andExpect(jsonPath("$.token").exists())
//                .andExpect(jsonPath("$.username").value(userExisted.getUsername()))
//                .andExpect(jsonPath("$.lastName").value(userExisted.getLastName()))
//                .andExpect(jsonPath("$.phoneNumber").value(userExisted.getPhoneNumber()))
                .andReturn();

    }


}
