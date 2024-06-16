package com.bordify.persistence;

import com.bordify.dtos.TopicListDTO;
import com.bordify.models.Board;
import com.bordify.models.Color;
import com.bordify.models.Topic;
import com.bordify.models.User;
import com.bordify.persistence.models.BoardModelTestService;
import com.bordify.persistence.models.ColorModelTestService;
import com.bordify.persistence.models.TopicModelTestService;
import com.bordify.persistence.models.UserModelTestService;
import com.bordify.repositories.BoardRepository;
import com.bordify.repositories.ColorRepository;
import com.bordify.repositories.TopicRepository;
import com.bordify.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@DataJpaTest
public class TopicRepositoryShould {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private BoardRepository  boardRepository;

    @Autowired
    private ColorRepository  colorRepository;

    @Autowired
    private UserRepository userRepository;


    @DisplayName("Should find one topic by boardId")
    @Test
    public void shouldFindOneTopicByBoardId() {

        User  userTest = UserModelTestService.createValidUser();
        userRepository.save(userTest);

        Board  boardTest = BoardModelTestService.createValidBoard(userTest);
        Board  noRalatedBoardTest = BoardModelTestService.createValidBoard(userTest);
        boardRepository.saveAll(List.of(boardTest,noRalatedBoardTest));

        Color colorTest = ColorModelTestService.createValidColor();
        colorRepository.save(colorTest);

        Topic topicTest = TopicModelTestService.createValidTopic(colorTest, boardTest);
        Topic noRalatedTopicTest = TopicModelTestService.createValidTopic(colorTest, noRalatedBoardTest);
        topicRepository.saveAll(List.of(topicTest, noRalatedTopicTest));

        Pageable pageable = Pageable.unpaged();
        List<TopicListDTO> topicFromDb = topicRepository.findByBoardIdCustom(topicTest.getBoardId(), pageable);

        // assert for verify that are not related topics
        Assertions.assertNotEquals(topicTest.getBoardId(), noRalatedTopicTest.getBoardId());

        Assertions.assertEquals(1, topicFromDb.size());

        Assertions.assertEquals(topicTest.getId(), topicFromDb.get(0).getId());

    }


}
