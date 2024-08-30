package com.bordify.persistence;

import com.bordify.models.User;
import com.bordify.repositories.UserRepository;
import com.bordify.shared.infrastucture.controlles.IntegrationTestBaseClass;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.bordify.persistence.models.UserModelTestService.createRandomUser;
import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest
@Transactional
public class UserRepositoryShould extends IntegrationTestBaseClass
{

    @Autowired
    private  UserRepository repository;
    private  User userTest;

    @BeforeEach
    public void setUp() {

        userTest = createRandomUser();
        repository.save(userTest);

    }


    @DisplayName("Should find user by email")
    @Test
    public void shouldFindUserByEmail() {

        boolean hasUser = repository.existsByEmail(userTest.getEmail());
        User user = repository.findByEmail(userTest.getEmail());

//        Assertions.assertTrue(hasUser);
//        Assertions.assertEquals(userTest, user);
        assertAll("User found by email",
                () -> assertTrue(hasUser, "User should exist by email"),
                () -> assertEquals(userTest, user, "Fetched user should match the test user")
        );
    }

    @DisplayName("Should find user by userName")
    @Test
    public void shouldFindUserByUsername() {
        boolean hasUser = repository.existsByUsername(userTest.getUsername());
        assertAll("User found by username",
                () -> assertTrue(hasUser, "User should exist by username"),
                () -> assertEquals(userTest, repository.findByUsername(userTest.getUsername()), "Fetched user should match the test user")
        );
    }

}