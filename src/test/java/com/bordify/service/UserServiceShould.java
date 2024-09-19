package com.bordify.service;

import com.bordify.controllers.auth.AuthJwtResponse;
import com.bordify.exceptions.DuplicateEmailException;
import com.bordify.exceptions.DuplicateUserNamelException;
import com.bordify.exceptions.UserNotFoundException;
import com.bordify.models.User;
import com.bordify.persistence.models.UserFactory;
import com.bordify.repositories.UserRepository;
import com.bordify.services.JwtService;
import com.bordify.services.UserService;
import com.bordify.shared.infrastucture.controlles.UnitTestBaseClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static com.bordify.shared.domain.FactoryValues.generateRandomAlphanumeric;
import static com.bordify.shared.domain.FactoryValues.generateRandomEmail;
import static com.bordify.utils.GeneratorValuesRandom.generateRandomValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


public class UserServiceShould extends UnitTestBaseClass {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private JwtService jwtServiceMock;

    @BeforeEach
    public void setUp() {
        Mockito.reset(userRepositoryMock, jwtServiceMock);
    }

    @DisplayName("shoud find a user by username")
    @Test
    public void shouldFindUserByUsername() {
        // Given
        User userTest = UserFactory.getRandomUser();
        String userName = userTest.getUsername();


        // When
        when(userRepositoryMock.findByUsername(userName)).thenReturn(userTest);
        User user = userService.getUserByUsername(userName);

        // Then
        Mockito.verify(userRepositoryMock, Mockito.times(1)).findByUsername(userName);
        assertEquals(userName, user.getUsername());
        assertEquals(userTest.getId(), user.getId());
    }

    @DisplayName("shoud throw an exeption when an user not found by username")
    @Test
    public void shouldThrowUserNotFoundExceptionWhenUserNotFound() {
        // Given
        String userName = "XXXX";
        when(userRepositoryMock.findByUsername(userName)).thenReturn(null);

        // When/Then
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.getUserByUsername(userName);
        });

    }

    @DisplayName("should create an User")
    @Test
    public void shouldCreateUser() {
        User userTest = UserFactory.getRandomUser();

        when(userRepositoryMock.save(userTest)).thenReturn(userTest);

        String token = "token";
        String refreshToken = "refreshToken";

//        when(
//            jwtServiceMock.getRefreshToken(userTest.getUsername())
//        ).thenReturn(refreshToken);

        when(
            jwtServiceMock.getAccessToken(userTest.getUsername()
            )).thenReturn(token);


        AuthJwtResponse response = userService.createUser(userTest);

        // Then
        Mockito.verify(userRepositoryMock, Mockito.times(1)).save(userTest);
        assertEquals(userTest.getId(), userTest.getId());
        assertEquals(userTest.getUsername(), userTest.getUsername());
        Assertions.assertNotNull(response.getToken());
//        Assertions.assertNotNull(response.getRefreshToken());

    }

    @DisplayName("shoud throw an exeption when we try create an user with existed email")
    @Test
    public void shouldThrowExceptionWhenCreateAnUserWithExistedEmail() {

        // Given
        User userTest = UserFactory.getRandomUser();
        String email = userTest.getEmail();

        String userName = generateRandomAlphanumeric(generateRandomValue(5,20));
        when(userRepositoryMock.existsByEmail(email)).thenReturn(true);

        // When/Then
        DuplicateEmailException exception = assertThrows(DuplicateEmailException.class, () -> {
            userService.createUser(userTest);
        });

    }

    @DisplayName("shoud throw an exeption when we try create an user with existed userName")
    @Test
    public void shouldThrowExceptionWhenCreateAnUserWithExistedUserName() {

        // Given
        User userTest = UserFactory.getRandomUser();
        String userName = userTest.getUsername();

        when(userRepositoryMock.existsByUsername(userName)).thenReturn(true);

        // When/Then
        DuplicateUserNamelException exception = assertThrows(DuplicateUserNamelException.class, () -> {
            userService.createUser(userTest);
        });

    }


}
