package com.bordify.persistence.models;

import com.bordify.models.Board;
import com.bordify.models.User;

import java.util.UUID;

import static com.bordify.shared.domain.FactoryValues.generateRandomAlphanumeric;

public class BoardModelTestService  {

    public static Board createValidBoard(User user) {

        UUID userId = user.getId();
        return Board.builder()
                .id(UUID.randomUUID())
                .name(generateRandomAlphanumeric(1, 50))
                .user(user)
                .userId(userId)
                .build();
    }

}
