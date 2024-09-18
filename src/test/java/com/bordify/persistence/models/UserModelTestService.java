package com.bordify.persistence.models;

import com.bordify.models.User;

import java.time.LocalTime;
import java.util.UUID;

import static com.bordify.shared.domain.FactoryValues.*;

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

    public static User createRandomUser() {
        LocalTime currentTime = LocalTime.now();

        return User.builder()
                .id(UUID.randomUUID())
                .username(generateRandomAlphanumeric(10))
                .password(generateRandomAlphanumeric(10))
                .email(generateRandomEmail())
                .firstName(generateRandomString(5))
                .lastName(generateRandomString(7))
                .isVerified(false)
                .phoneNumber(generateRandomPhoneNumber())
                .created(currentTime)
                .lastLogin(null)
                .build();
    }



}
