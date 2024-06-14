package com.bordify.persistence.models;

import com.bordify.models.User;

import java.time.LocalTime;
import java.util.UUID;

public class UserModelTestService {

    public static User  createValidUser() {

        LocalTime currentTime = LocalTime.now();

        User user = User.builder()
                .id(UUID.randomUUID())
                .username("john_doe")
                .password("password123")
                .email("john.doe@example.com")
                .firstName("John")
                .lastName("Doe")
                .isVerified(false)
                .phoneNumber("+1234567890")
                .created(currentTime)
                .lastLogin(currentTime)
                .build();

        return user;
    }


}
