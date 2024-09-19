package com.bordify.service;

import com.bordify.dtos.BoardListDTO;
import com.bordify.exceptions.EntityNotFound;
import com.bordify.exceptions.InvalidBoardNameException;
import com.bordify.models.Board;
import com.bordify.models.User;
import com.bordify.persistence.models.BoardFactory;
import com.bordify.persistence.models.UserFactory;
import com.bordify.repositories.BoardRepository;
import com.bordify.services.BoardService;
import com.bordify.shared.infrastucture.controlles.UnitTestBaseClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

import static com.bordify.shared.domain.FactoryValues.generateRandomAlphanumeric;
import static com.bordify.utils.GeneratorValuesRandom.generateRandomValue;
import static org.mockito.Mockito.when;

public class BoardServiceShould extends UnitTestBaseClass {

    @Mock
    BoardRepository repositoryMock;

    @InjectMocks
    private BoardService boardService;

    User user;
    Board board;

    @BeforeEach
    public void setup(){
        this.user = UserFactory.getRandomUser();
        this.board = BoardFactory.getRandomBoard(this.user);
    }

    @DisplayName("should create a board")
    @Test
    public void shouldCreateABoard(){

        when(repositoryMock.save(board)).thenReturn(board);

        boardService.createBoard(board);

        Mockito.verify(repositoryMock, Mockito.times(1)).save(board);
    }

    @DisplayName("should delete a board")
    @Test
    public void shouldDeleteABoard(){

        when(repositoryMock.save(board)).thenReturn(board);

        boardService.createBoard(board);

        Mockito.verify(repositoryMock, Mockito.times(1)).save(board);
    }

    @DisplayName("shoud find a list of boards")
    @Test
    public void shouldFindListOfBoards(){

        Pageable pageable = Pageable.ofSize(generateRandomValue(3,20));
        UUID userId = UUID.randomUUID();
        Page<BoardListDTO> boards = Page.empty();
        when(repositoryMock.findByUserId(pageable,userId)).thenReturn(boards);

        boardService.listBoards(pageable, userId);
        Mockito.verify(repositoryMock, Mockito.times(1)).findByUserId(pageable,userId);

    }

    @DisplayName("should update name of board")
    @Test
    public void shouldUpdateNameOfBouard(){

        UUID boardId = board.getId();

        Board boardToUpdate = Board.builder()
                        .id(board.getId())
                        .name(generateRandomAlphanumeric(generateRandomValue(1,50)))
                        .userId(board.getUserId())
                        .build();

        when(repositoryMock.existsById(boardId)).thenReturn(true);
        when(repositoryMock.findById(boardId)).thenReturn(Optional.of(board));
//        when(repositoryMock.save(boardToUpdate)).thenReturn(boardToUpdate);

        Board boardUpdated = boardService.update(boardToUpdate);

        Mockito.verify(repositoryMock,Mockito.times(1)).existsById(boardId);
        Mockito.verify(repositoryMock,Mockito.times(1)).findById(boardId);
        Mockito.verify(repositoryMock,Mockito.times(1)).save(Mockito.any());

        Assertions.assertEquals(boardToUpdate.getName(), boardUpdated.getName());
        Assertions.assertEquals(boardToUpdate.getId(), boardUpdated.getId());

    }

    @DisplayName("shouldn't an not existed board")
    @Test
    public void shouldntUpdateAnNotexistedBouard(){


        UUID boardId = board.getId();

        Board boardToUpdate = Board.builder()
                .id(board.getId())
                .name(generateRandomAlphanumeric(generateRandomValue(1,50)))
                .userId(board.getUserId())
                .build();

        when(repositoryMock.existsById(boardId)).thenReturn(false);

        EntityNotFound entityNotFound = Assertions.assertThrows(EntityNotFound.class, ()->{
            boardService.update(boardToUpdate);
        });

        Mockito.verify(repositoryMock,Mockito.times(1)).existsById(boardId);
    }

    // Todo create test to verify that we cant create or update board with void name

    @DisplayName("shouldn't update a board with void name")
    @Test
    public void shouldntUpdateWithAVoidName(){

        UUID boardId = board.getId();

        board.setName(" ");

        when(repositoryMock.existsById(boardId)).thenReturn(true);

        Assertions.assertThrows(InvalidBoardNameException.class, ()->{
            boardService.update(board);
        });


        Mockito.verify(repositoryMock,Mockito.times(1)).existsById(boardId);
        Mockito.verify(repositoryMock,Mockito.times(0)).findById(boardId);
        Mockito.verify(repositoryMock,Mockito.times(0)).save(Mockito.any());

        Mockito.reset(repositoryMock);
        board.setName("");

        when(repositoryMock.existsById(boardId)).thenReturn(true);

        Assertions.assertThrows(InvalidBoardNameException.class, ()->{
            boardService.update(board);
        });


        Mockito.verify(repositoryMock,Mockito.times(1)).existsById(boardId);
        Mockito.verify(repositoryMock,Mockito.times(0)).findById(boardId);
        Mockito.verify(repositoryMock,Mockito.times(0)).save(Mockito.any());

    }

    @DisplayName("shouldn't update a board with null name")
    @Test
    public void shouldntUpdateWithANullName(){

        UUID boardId = board.getId();

        board.setName(null);

        when(repositoryMock.existsById(boardId)).thenReturn(true);

        Assertions.assertThrows(InvalidBoardNameException.class, ()->{
            boardService.update(board);
        });


        Mockito.verify(repositoryMock,Mockito.times(1)).existsById(boardId);
        Mockito.verify(repositoryMock,Mockito.times(0)).findById(boardId);
        Mockito.verify(repositoryMock,Mockito.times(0)).save(Mockito.any());

    }

    @DisplayName("should create a board non null or void name")
    @Test
    public void shouldCreateBoardIfNameIsNotVoidOrNull(){

        board.setName("");

        Assertions.assertThrows(InvalidBoardNameException.class, ()->{
            boardService.createBoard(board);
        });
//
        Mockito.verify(repositoryMock, Mockito.times(0)).save(board);

        Mockito.reset(repositoryMock);

        board.setName(" ");

        Assertions.assertThrows(InvalidBoardNameException.class, ()->{
            boardService.createBoard(board);
        });

        Mockito.verify(repositoryMock, Mockito.times(0)).save(board);

        Mockito.reset(repositoryMock);

        board.setName(null);

        Assertions.assertThrows(InvalidBoardNameException.class, ()->{
            boardService.createBoard(board);
        });

        Mockito.verify(repositoryMock, Mockito.times(0)).save(board);
    }


    @DisplayName(" should be owner of board")
    @Test
    public void shouldBeOwnerOfBoard(){

        UUID boardId = board.getId();
        UUID userId = user.getId();

        when(repositoryMock.findById(boardId)).thenReturn(Optional.of(board));

        boolean isUserOwnerOfBoard = boardService.isUserOwnerOfBoard(boardId, userId);

        Mockito.verify(repositoryMock,Mockito.times(1)).findById(boardId);

        Assertions.assertTrue(isUserOwnerOfBoard);

    }

    @DisplayName(" should throw an exception when is not owner of board")
    @Test
    public void shouldThrowAnExceptionWhenIsNotOwnerOfBoard(){

        UUID boardId = board.getId();
        UUID userIdNotRelated = UUID.randomUUID();

        when(repositoryMock.findById(boardId)).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFound.class, ()->{
            boardService.isUserOwnerOfBoard(boardId, userIdNotRelated);
        });


        Mockito.verify(repositoryMock,Mockito.times(1)).findById(boardId);

    }

}
