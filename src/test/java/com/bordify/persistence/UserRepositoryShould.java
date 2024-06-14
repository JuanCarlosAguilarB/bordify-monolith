package com.bordify.persistence;
import com.bordify.models.User;
import com.bordify.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;
@DataJpaTest
public class UserRepositoryShould {

    @Autowired
    private UserRepository userRepository;

    @DisplayName("Should find user by email")
    @Test
    public void shouldFindUserByEmail() {
        User userTest = createValidEntity();
        userRepository.save(userTest);

        boolean hasUser = userRepository.existsByEmail(userTest.getEmail());

        Assertions.assertTrue(hasUser);
    }

    @DisplayName("Should find user by userName")
    @Test
    public void shouldFindUserByUsername() {
        User userTest = createValidEntity();
        userRepository.save(userTest);

        boolean hasUser = userRepository.existsByUsername(userTest.getUsername());

        Assertions.assertTrue(hasUser);
    }

    @DisplayName("Should get an user by userName")
    @Test
    public void shouldGetAnUserByUsername() {
        User userTest = createValidEntity();
        userRepository.save(userTest);

        User user = userRepository.findByUsername(userTest.getUsername());

        Assertions.assertEquals(userTest, user);
    }


    @DisplayName("Should get an user by email")
    @Test
    public void shouldGetAnUserByEmail() {
        User userTest = createValidEntity();
        userRepository.save(userTest);

        User user = userRepository.findByEmail(userTest.getEmail());

        Assertions.assertEquals(userTest, user);
    }


    public User createValidEntity() {
        return User.builder()
                .id(UUID.randomUUID())
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();
    }
}