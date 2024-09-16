package com.bordify.persistence;


import com.bordify.models.Board;
import com.bordify.models.User;
import com.bordify.persistence.models.BoardModelTestService;
import com.bordify.persistence.models.UserModelTestService;
import com.bordify.repositories.BoardRepository;
import com.bordify.repositories.UserRepository;
import com.bordify.shared.infrastucture.controlles.IntegrationTestBaseClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BoardRepositoryShould extends IntegrationTestBaseClass {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;

    Board boardTest;

    @BeforeEach
    public void setUp() {
        User userTest = UserModelTestService.createValidUser();
        userRepository.save(userTest);

        boardTest = BoardModelTestService.createValidBoard(userTest);
        boardRepository.save(boardTest);
    }


    @DisplayName("Should find Board by id")
    @Test
    public void shouldDeleteBoardById() {

        Board board = boardRepository.findById(boardTest.getId()).get();

        assertNotNull(board);
        assertThat(board.getId()).isEqualTo(boardTest.getId());

        boardRepository.deleteById(boardTest.getId());

        Optional<Board> deletedBoard = boardRepository.findById(boardTest.getId());
        Assertions.assertFalse(deletedBoard.isPresent());

    }




}
