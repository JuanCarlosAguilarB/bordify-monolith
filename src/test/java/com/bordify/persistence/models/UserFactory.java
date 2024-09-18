package com.bordify.persistence.models;

import com.bordify.models.Board;
import com.bordify.models.User;
import com.bordify.repositories.UserRepository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bordify.shared.domain.FactoryValues.*;
import static com.bordify.utils.GeneratorValuesRandom.generateRandomValue;

public class UserFactory {

    UserRepository userRepository;

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

    public UserFactory(UserRepository userRepository){
        this.userRepository= userRepository;
    }

    public User getRandomUserPersisted() {

        User user = getRandomUser();
        userRepository.save(user);
        return user;
    }

    public List<User> getUsersPersisted(int amountUsers){
        List<User> users = new ArrayList<>();
        for (int i =0; i<amountUsers; i++){
            users.add(getRandomUserPersisted());
        }
        return users;
    }

}
