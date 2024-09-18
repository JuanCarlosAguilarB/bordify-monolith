package com.bordify.persistence;

import com.bordify.models.User;
import com.bordify.persistence.models.UserFactory;
import com.bordify.repositories.UserRepository;
import com.bordify.shared.infrastucture.controlles.IntegrationTestBaseClass;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
public class UserRepositoryShould extends IntegrationTestBaseClass
{

    @Autowired
    private  UserRepository repository;
    private UserFactory userFactory ;//= new UserFactory(repository);
    private  User userTest;

    @Autowired
    public UserRepositoryShould(UserRepository repository){
        userFactory = new UserFactory(repository);

    }

    @BeforeEach
    public void setUp() {
        userTest = userFactory.getRandomUserPersisted();
    }


    @DisplayName("Should find user by email")
    @Test
    public void shouldFindUserByEmail() {

        boolean hasUser = repository.existsByEmail(userTest.getEmail());
        User userFound = repository.findByEmail(userTest.getEmail());

//        Assertions.assertTrue(hasUser);
//        Assertions.assertEquals(userTest, user);
        assertAll("User found by email",
                () -> assertTrue(hasUser, "User should exist by email"),
                () -> assertEquals(userTest.getId(), userFound.getId(), "Fetched user should match the test user"),
                () -> assertEquals(userTest.getUsername(), userFound.getUsername()),
                () -> assertEquals(userTest.getCreated(), userFound.getCreated()),
                () -> assertEquals(userTest.getFirstName(), userFound.getFirstName()),
                () -> assertEquals(userTest.getIsVerified(), userFound.getIsVerified()),
                () -> assertEquals(userTest.getPassword(), userFound.getPassword()),
                () -> assertEquals(userTest.getEmail(), userFound.getEmail()),
                () -> assertEquals(userTest.getLastName(), userFound.getLastName()),
                () -> assertEquals(userTest.getPhoneNumber(), userFound.getPhoneNumber())
        );
    }

    @DisplayName("Should find user by userName")
    @Test
    public void shouldFindUserByUsername() {
        boolean hasUser = repository.existsByUsername(userTest.getUsername());
        User userFound = repository.findByUsername(userTest.getUsername());

        assertAll("User found by username",
                () -> assertTrue(hasUser, "User should exist by username"),
                () -> assertEquals(userTest.getId(), userFound.getId(), "Fetched user should match the test user"),
                () -> assertEquals(userTest.getUsername(), userFound.getUsername()),
                () -> assertEquals(userTest.getCreated(), userFound.getCreated()),
                () -> assertEquals(userTest.getFirstName(), userFound.getFirstName()),
                () -> assertEquals(userTest.getIsVerified(), userFound.getIsVerified()),
                () -> assertEquals(userTest.getPassword(), userFound.getPassword()),
                () -> assertEquals(userTest.getEmail(), userFound.getEmail()),
                () -> assertEquals(userTest.getLastName(), userFound.getLastName()),
                () -> assertEquals(userTest.getPhoneNumber(), userFound.getPhoneNumber())
        );
    }

}