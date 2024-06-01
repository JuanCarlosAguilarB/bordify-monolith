package com.bordify.services;

import com.bordify.controllers.auth.AuthJwtResponse;
//import com.bordify.events.CreateUserDomainEvent;
import com.bordify.exceptions.DuplicateEmailException;
import com.bordify.exceptions.UserNotFoundException;
import com.bordify.models.User;
import com.bordify.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Service class for user-related operations.
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

//    @Autowired
//    private RabbitService rabbitService;

    /**
     * Creates a new user with the provided user details and generates an authentication token.
     *
     * @param user The user object containing the user details to be created.
     * @return An AuthJwtResponse object containing the generated authentication token.
     * @throws DuplicateEmailException If the provided email or username is already registered.
     */
    public AuthJwtResponse createUser(User user) {

        if (userRepository.existsByEmail(user.getEmail()) && userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateEmailException("Both the email " + user.getEmail() + " and username " + user.getUsername() + " are already registered. Please use different credentials.");
        } else if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateEmailException("The email " + user.getEmail() + " is already registered. Please use a different email.");
        } else if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateEmailException("The username " + user.getUsername() + " is already registered. Please use a different username.");
        }

        userRepository.save(user);

//        CreateUserDomainEvent createUserDomainEvent = new CreateUserDomainEvent(
//                user.getId().toString(),
//                user.getUsername(),
//                user.getEmail(),
//                user.getPassword(),
//                user.getFirstName(),
//                user.getLastName(),
//                user.getIsVerified(),
//                user.getPhoneNumber(),
//                user.getCreated(),
//                user.getLastLogin()
//        );

//        rabbitService.publish(createUserDomainEvent);

        String token = jwtService.getAccessToken(user.getUsername());
        return AuthJwtResponse.builder()
                .token(token)
                .build();
    }

    /**
     * Retrieves a user by the specified username.
     *
     * @param username The username of the user to retrieve.
     * @return The User object corresponding to the specified username.
     * @throws UserNotFoundException If no user is found with the specified username.
     */
    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return user;
    }


}
