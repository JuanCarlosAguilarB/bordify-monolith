package com.bordify.persistence.models;

import com.bordify.models.User;
import com.bordify.repositories.UserRepository;

import java.time.LocalTime;
import java.util.UUID;

import static com.bordify.shared.domain.FactoryValues.*;
import static com.bordify.utils.GeneratorValuesRandom.generateRandomValue;

public class UserFactory {

    UserRepository userRepository;

    public UserFactory(UserRepository userRepository){
        this.userRepository= userRepository;
    }

    public User getRandomUserPersisted() {

        LocalTime currentTime = LocalTime.now();
        User user = User.builder()
                .id(UUID.randomUUID())
                .username(generateRandomAlphanumeric(generateRandomValue(5,20)))
                .password(generateRandomAlphanumeric(generateRandomValue(5,20)))
                .email(generateRandomEmail())
                .firstName(generateRandomString(generateRandomValue(5,20)))
                .lastName(generateRandomString(generateRandomValue(5,20)))
                .isVerified(false)
                .phoneNumber(generateRandomPhoneNumber())
                .created(currentTime)
                .lastLogin(null)
                .build();
        userRepository.save(user);
        return user;
    }

    public static User getRandomUser() {
        LocalTime currentTime = LocalTime.now();
        return User.builder()
                .id(UUID.randomUUID())
                .username(generateRandomAlphanumeric(generateRandomValue(5,20)))
                .password(generateRandomAlphanumeric(generateRandomValue(5,20)))
                .email(generateRandomEmail())
                .firstName(generateRandomString(generateRandomValue(5,20)))
                .lastName(generateRandomString(generateRandomValue(5,20)))
                .isVerified(false)
                .phoneNumber(generateRandomPhoneNumber())
                .created(currentTime)
                .lastLogin(null)
                .build();
    }


}
