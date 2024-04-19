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

import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    BoardRepository boardRepository;

    public void createBoard(Board board) {
        try {
            boardRepository.save(board);
        } catch (Exception e) {
            throw new ResourceNotCreatedException("Error creating board");
        }
    }

    public void deleteBoard(UUID boardId) {

        if (!boardRepository.existsById(boardId)) {
            throw new EntityNotFound("Error deleting board: Board not found");
        }

        boardRepository.deleteById(boardId);

    }
    public Page<BoardListDTO> listBoards (Pageable pageable, UUID userId) {
        return boardRepository.findByUserId(pageable, userId);
    }

    public Board update(Board board) {

        ensureBoardExist(board);

        Board boardToUpdate = boardRepository.findById(board.getId()).get();

        UpdateFieldsOfClasses.updateFields(boardToUpdate, board);

        boardRepository.save(boardToUpdate);

        Board boardToResponse = Board.builder()
                .id(boardToUpdate.getId())
                .name(boardToUpdate.getName())
                .build();

        return boardToResponse;

    }

    public void ensureBoardExist(Board board) {
        if (!boardRepository.existsById(board.getId())) {
            throw new EntityNotFound("Board not found");
        }
    }

}
