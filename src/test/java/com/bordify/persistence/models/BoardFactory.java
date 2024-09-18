package com.bordify.persistence.models;

import com.bordify.models.Board;
import com.bordify.models.User;
import com.bordify.repositories.BoardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bordify.shared.domain.FactoryValues.generateRandomAlphanumeric;
import static com.bordify.utils.GeneratorValuesRandom.generateRandomValue;

public class BoardFactory {

    BoardRepository boardRepository;


    public BoardFactory(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }


    public static Board getRandomBoard(User user) {

        return Board.builder()
                .id(UUID.randomUUID())
                .name(generateRandomAlphanumeric(generateRandomValue(1,50)))
                .user(user)
                .userId(user.getId())
                .build();
    }


    public Board getRandomBoardPersisted(User user) {

        Board board = getRandomBoard(user);
        boardRepository.save(board);
        return board;
    }


    public List<Board> getRandomBoardsPersisted(User user, int amountBoards){

        List<Board> boards = new ArrayList<>();
        for (int i =0; i<amountBoards; i++){
            Board board = getRandomBoard(user);
            boards.add(board);
        }
        return boards;
    }
}
