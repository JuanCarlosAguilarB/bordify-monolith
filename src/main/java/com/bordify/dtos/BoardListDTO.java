package com.bordify.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

/**
 * Represents a data transfer object (DTO) for displaying basic information about a board.
  * This class uses Lombok annotations to simplify the boilerplate code for getter, setter, and constructors.
 */
@Data
@AllArgsConstructor
public class BoardListDTO {

    /**
     * The unique identifier of the board.
     */
    private UUID id;

    /**
     * The name of the board.
     */
    private String name;
}
