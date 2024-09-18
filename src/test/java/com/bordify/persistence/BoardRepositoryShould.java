package com.bordify.persistence;


import com.bordify.dtos.BoardListDTO;
import com.bordify.models.Board;
import com.bordify.models.User;
import com.bordify.persistence.models.BoardFactory;
import com.bordify.persistence.models.UserFactory;
import com.bordify.repositories.BoardRepository;
import com.bordify.repositories.UserRepository;
import com.bordify.shared.infrastucture.controlles.IntegrationTestBaseClass;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

import static com.bordify.utils.GeneratorValuesRandom.generateRandomValue;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
public class BoardRepositoryShould extends IntegrationTestBaseClass {

    private BoardRepository boardRepository;
    private UserFactory userFactory;
    private BoardFactory boardFactory;
    private Board boardTest;
    private User userTest;

    @Autowired
    public BoardRepositoryShould(UserRepository userRepository, BoardRepository boardRepository){
        this.boardRepository = boardRepository;
        this.userFactory = new UserFactory(userRepository);
        this.boardFactory = new BoardFactory(boardRepository);
    }

    @BeforeEach
    public void setUp() {
        this.userTest = userFactory.getRandomUserPersisted();
        this.boardTest = boardFactory.getRandomBoardPersisted(this.userTest);
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

        // create boards for others users
        userFactory.getUsersPersisted(amountUsers).forEach(user->
                boardFactory.getRandomBoardsPersisted(user,amountBoardsPerUser)
        );

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
        userFactory.getUsersPersisted(amountUsers).forEach(user->
                        boardFactory.getRandomBoardsPersisted(user,amountBoardsPerUser)
                );

        // create more boards for user of test
        boardFactory.getRandomBoardsPersisted(userTest, amountBoardsPerUser);


        Pageable pageable = Pageable.ofSize(pageSize);
        Page<BoardListDTO> boardsPageResult =  boardRepository.findByUserId(pageable,userTest.getId());
        List<BoardListDTO> boardList = boardsPageResult.getContent();

//        assertNotNull(board);
        boardList.forEach(board -> assertThat(board.getUserId()).isEqualTo(userTest.getId()));
//        assertEquals(boardTest, board);

    }

}
