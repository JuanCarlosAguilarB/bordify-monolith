package com.bordify.persistence.models;

import com.bordify.models.Board;

import com.bordify.models.User;

import java.util.UUID;

public class BoardModelTestService  {

    public static Board createValidBoard(User user) {

//        User user = UserModelTestService.createValidUser();
        UUID userId = user.getId();

        return Board.builder()
                .id(UUID.randomUUID())
                .name("Test Board")
                .user(user)
                .userId(userId)
                .build();
    }

}
