package com.bordify.persistence;
import com.bordify.models.User;
import com.bordify.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;
@DataJpaTest
public class UserRepositoryShould {

    @Autowired
    private UserRepository userRepository;


    @Test
    public void shouldFindUserByEmail() {

        // Arrange
        User userTest = createValidEntity();
        userRepository.save(userTest);

        // Act
        boolean hasUser = userRepository.existsByEmail(userTest.getEmail());

        // Assert

        Assertions.assertTrue(hasUser);
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