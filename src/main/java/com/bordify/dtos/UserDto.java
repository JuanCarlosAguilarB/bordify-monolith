package com.bordify.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents a data transfer object (DTO) for transferring user information.
 */
@Data
@AllArgsConstructor
public class UserDto {

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The first name of the user.
     */
    private String firstName;

    /**
     * The last name of the user.
     */
    private String lastName;

    /**
     * The phone number of the user.
     */
    private String phoneNumber;
}