package com.bordify.persistence;


import com.bordify.dtos.BoardListDTO;
import com.bordify.models.Board;
import com.bordify.models.User;
import com.bordify.repositories.BoardRepository;
import com.bordify.repositories.UserRepository;
import com.bordify.shared.infrastucture.controlles.IntegrationTestBaseClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.bordify.persistence.models.BoardModelTestService.createValidBoard;
import static com.bordify.persistence.models.UserModelTestService.createValidUser;
import static com.bordify.utils.GeneratorValuesRandom.generateRandomValue;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class BoardRepositoryShould extends IntegrationTestBaseClass {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;

    Board boardTest;
    User userTest;

    @BeforeEach
    public void setUp() {
        userTest = createUser();
        boardTest = createBoards(this.userTest,1).get(0);
    }


    @DisplayName("Should find Board by id")
    @Test
    public void shouldDeleteBoardById() {

        Board board = boardRepository.findById(boardTest.getId()).get();

        assertNotNull(board);
        assertThat(board.getId()).isEqualTo(boardTest.getId());

        boardRepository.deleteById(boardTest.getId());

        Optional<Board> deletedBoard = boardRepository.findById(boardTest.getId());
        assertFalse(deletedBoard.isPresent());

    }

    @DisplayName("Should exist board by Id")
    @Test
    public void shouldExistBoardById(){
        assertTrue(boardRepository.existsById(boardTest.getId()));
    }

    @DisplayName("Should find one Board by user id")
    @Test
    public void shouldFindBoardByUserId() {

        int amountUsers = generateRandomValue(0, 5);
        int amountBoardsPerUser = generateRandomValue(3, 15);
        int pageSize = generateRandomValue(1, amountBoardsPerUser);

        for (int i=0; i<amountUsers; i++){
            createBoards(createUser(),amountBoardsPerUser);
        }

        Pageable pageable = Pageable.ofSize(pageSize);
        Page<BoardListDTO> boardsPageResult =  boardRepository.findByUserId(pageable,userTest.getId());
        List<BoardListDTO> boardList = boardsPageResult.getContent();

//        assertNotNull(board);

        assertEquals(boardList.size(), 1);
        assertThat(boardList.get(0).getUserId()).isEqualTo(userTest.getId());
        assertThat(boardList.get(0).getName()).isEqualTo(boardTest.getName());

//        assertEquals(boardTest, board);

    }

    @DisplayName("Should find some Boards by user id")
    @Test
    public void shouldFindBoardsByUserId() {

        int amountUsers = generateRandomValue(0, 5);
        int amountBoardsPerUser = generateRandomValue(3, 15);
        int pageSize = generateRandomValue(1, amountBoardsPerUser);

        // create boards for others users
        for (int i=0; i<amountUsers; i++){
            createBoards(createUser(),amountBoardsPerUser);
        }

        // create more boards for user of test
        createBoards(userTest, amountBoardsPerUser);


        Pageable pageable = Pageable.ofSize(pageSize);
        Page<BoardListDTO> boardsPageResult =  boardRepository.findByUserId(pageable,userTest.getId());
        List<BoardListDTO> boardList = boardsPageResult.getContent();

//        assertNotNull(board);
        boardList.forEach(board -> assertThat(board.getUserId()).isEqualTo(userTest.getId()));
//        assertEquals(boardTest, board);

    }

    private User createUser(){
        User user = createValidUser();
        userRepository.save(user);
        return user;
    }


    private List<Board> createBoards(User user, int amountBoards){
        List<Board> boards = new ArrayList<>();
        for (int i =0; i<amountBoards; i++){
            Board board = createValidBoard(user);
            boardRepository.save(board);
            boards.add(board);
        }
        return boards;
    }

}
