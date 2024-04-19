package com.bordify.repositories;

import com.bordify.models.Board;
import com.bordify.dtos.BoardListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Repository interface for accessing and managing board entities in the database.
 */
public interface BoardRepository extends JpaRepository<Board, UUID> {

    /**
     * Deletes a board by its ID.
     *
     * @param boardId The ID of the board to delete.
     */
    public void deleteById(UUID boardId);

    /**
     * Checks if a board exists by its ID.
     *
     * @param boardId The ID of the board to check.
     * @return True if the board exists, false otherwise.
     */
    public boolean existsById(UUID boardId);

    /**
     * Retrieves a page of board DTOs.
     *
     * @param pageable The pagination information.
     * @return A page of board DTOs.
     */
    public Page<BoardListDTO> findBy(Pageable pageable);

    /**
     * Retrieves a page of board DTOs filtered by user ID.
     *
     * @param pageable The pagination information.
     * @param userId The ID of the user.
     * @return A page of board DTOs filtered by user ID.
     */
    public Page<BoardListDTO> findByUserId(Pageable pageable, UUID userId);

    /**
     * Finds a board DTO by its ID.
     *
     * @param boardId The ID of the board.
     * @return The board DTO.
     */
    public Board findDtoById(UUID boardId);
}
