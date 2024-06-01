package com.bordify.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.UUID;

/**
 * Represents a user entity in the Bordify application.
 * This class defines the structure of a user entity, including their username, password, email,
 * name, verification status, phone number, and timestamps for creation and last login.
 *
 * The `User` class leverages Lombok annotations to reduce boilerplate code, generating getters,
 * setters, constructors, and a builder pattern. Additionally, the `@Table` annotation is used
 * to specify the database table name as `"\"user\""`, which ensures compatibility with databases
 * where `user` is a reserved keyword or requires case-sensitive handling.
 */
@Entity
@Data
@Builder
@Table(name = "\"user\"")
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * Unique identifier for the User. It is automatically generated and uses UUID as the ID type.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Username of the user. This field must be unique.
     */
    @Column(unique = true)
    private String username;

    /**
     * Password of the user.
     */
    private String password;

    /**
     * Email address of the user.
     */
    private String email;

    /**
     * First name of the user.
     */
    private String firstName;

    /**
     * Last name of the user.
     */
    private String lastName;

    /**
     * Flag indicating whether the user is verified or not.
     */
    private Boolean isVerified = false;

    /**
     * Phone number of the user.
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * Time when the user was created.
     */
    private LocalTime created;

    /**
     * Time of the last login of the user.
     */
    @Column(name = "last_login")
    private LocalTime lastLogin;

}
