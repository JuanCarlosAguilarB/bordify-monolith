package com.bordify.config;

import com.bordify.models.User;
import com.bordify.exceptions.UserNotFoundException;
import com.bordify.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

/**
 * Configuration class for application-wide configurations.
 * Configures password encoding, user details service, authentication manager, and exception handling.
 */
@Configuration
@RequiredArgsConstructor

public class ApplicationConfig {

    private final UserRepository userRepository;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * Configures a WebSecurityCustomizer bean for ignoring certain requests.
     *
     * @return A WebSecurityCustomizer instance for ignoring specific requests.
     */
    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
//                "/user",
                "/ignore2"
        );
    }

    /**
     * Configures an AuthenticationManager bean for managing authentication.
     *
     * @param config The AuthenticationConfiguration for building the authentication manager.
     * @return An AuthenticationManager instance for managing authentication.
     * @throws Exception If an error occurs during authentication manager configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    }

    /**
     * Configures a UserDetailsService bean for loading user details by username.
     *
     * @return A UserDetailsService instance for loading user details.
     */
    @ExceptionHandler({ UserNotFoundException.class })
    @Bean
    public UserDetailsService userDetailService() {
        return new UserDetailsService() {
            @Autowired
            private UserRepository userRepository;

            @Override
            public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
//                User user = userRepository.findByEmail(email);
                User user = userRepository.findByUsername(email);


                if (user == null) {
                    throw new UserNotFoundException("Usuario no encontrado con el username: " + email);
                }
//                return new org.springframework.security.core.userdetails.User(
//                        user.getEmail(), user.getPassword(), user.getRoles());
//            }
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(), user.getPassword(),
                    true, true, true, true, Collections.emptyList());
            }

        };
    }

    /**
     * Configures an AuthenticationProvider bean for authenticating users.
     *
     * @return An AuthenticationProvider instance for user authentication.
     */
    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider authenticationProvider= new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

}
