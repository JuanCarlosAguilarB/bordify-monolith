package com.bordify.service;

import com.bordify.controllers.auth.AuthJwtResponse;
import com.bordify.exceptions.UserNotFoundException;
import com.bordify.models.User;
import com.bordify.persistence.models.UserModelTestService;
import com.bordify.repositories.UserRepository;
import com.bordify.services.JwtService;
import com.bordify.services.UserService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceShould {

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

    @Test
    public void shouldFindUserByUsername() {
        // Given
        String userName = "XXXX";
        User userTest = UserModelTestService.createValidUser();
        userTest.setUsername(userName);

        // When
        when(userRepositoryMock.findByUsername(userName)).thenReturn(userTest);
        User user = userService.getUserByUsername(userName);

        // Then
        Mockito.verify(userRepositoryMock, Mockito.times(1)).findByUsername(userName);
        assertEquals(userName, user.getUsername());
        assertEquals(userTest.getId(), user.getId());
    }

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

    @Test
    public void shouldCreateUser() {
        User userTest = UserModelTestService.createValidUser();

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


}
