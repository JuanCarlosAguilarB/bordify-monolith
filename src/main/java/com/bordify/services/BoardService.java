package com.bordify.services;


import com.bordify.dtos.BoardListDTO;
import com.bordify.exceptions.EntityNotFound;
import com.bordify.exceptions.ResourceNotCreatedException;
import com.bordify.models.Board;
import com.bordify.repositories.BoardRepository;
import com.bordify.utils.UpdateFieldsOfClasses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Service class for managing board operations.
 * This service handles creation, deletion, updating, and listing of boards.
 */
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    /**
     * Creates a new board and saves it in the database.
     *
     * @param board The board entity to be saved.
     * @throws ResourceNotCreatedException if the board cannot be created.
     */
    public void createBoard(Board board) {
        try {
            boardRepository.save(board);
        } catch (Exception e) {
            throw new ResourceNotCreatedException("Error creating board");
        }
    }

    /**
     * Deletes a board by its ID.
     *
     * @param boardId The UUID of the board to delete.
     * @throws EntityNotFound if no board exists with the given ID.
     */
    public void deleteBoard(UUID boardId) {
        if (!boardRepository.existsById(boardId)) {
            throw new EntityNotFound("Error deleting board: Board not found");
        }
        boardRepository.deleteById(boardId);
    }

    /**
     * Lists all boards for a given user.
     *
     * @param pageable Pagination information.
     * @param userId  The UUID of the user whose boards to list.
     * @return A page of {@link BoardListDTO} objects.
     */
    public Page<BoardListDTO> listBoards(Pageable pageable, UUID userId) {
        return boardRepository.findByUserId(pageable, userId);
    }

    /**
     * Updates an existing board.
     *
     * @param board The updated board entity.
     * @return The updated board entity with selective information.
     * @throws EntityNotFound if the board to update does not exist.
     */
    public Board update(Board board) {
        ensureBoardExist(board);

        Board boardToUpdate = boardRepository.findById(board.getId()).get();

        UpdateFieldsOfClasses.updateFields(boardToUpdate, board);

        boardRepository.save(boardToUpdate);

        return Board.builder()
                .id(boardToUpdate.getId())
                .name(boardToUpdate.getName())
                .build();
    }

    /**
     * Ensures that a board exists.
     *
     * @param board The board to check existence for.
     * @throws EntityNotFound if the board does not exist.
     */
    public void ensureBoardExist(Board board) {
        if (!boardRepository.existsById(board.getId())) {
            throw new EntityNotFound("Board not found");
        }
    }

    /**
     * Checks if a user is the owner of a board.
     *
     * @param boardId The ID of the board to check ownership for.
     * @param userId The ID of the user to check ownership against.
     * @return True if the user is the owner of the board, false otherwise.
     * @throws EntityNotFound if the board with the specified ID does not exist.
     */
    public Boolean isUserOwnerOfBoard(UUID boardId, UUID userId) {
        Optional<Board> board = Optional.ofNullable(boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFound("Board not found")));
        return board.get().getUserId().equals(userId);
    }


}