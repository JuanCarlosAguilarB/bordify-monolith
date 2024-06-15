package com.bordify.persistence;

import com.bordify.dtos.TopicListDTO;
import com.bordify.models.Board;
import com.bordify.models.Topic;
import com.bordify.persistence.models.BoardModelTestService;
import com.bordify.persistence.models.TopicModelTestService;
import com.bordify.repositories.TopicRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
public class TopicRepositoryShuold {

    @Autowired
    private TopicRepository topicRepository;

    @DisplayName("Should find one topic by boardId")
    @Test
    public void shouldFindOneTopicByBoardId() {

        Topic topicTest = TopicModelTestService.createValidTopic();
        Topic noRalatedTopicTest = TopicModelTestService.createValidTopic();

        List<Topic> topicList = new ArrayList<>();
        topicList.add(topicTest);
        topicList.add(noRalatedTopicTest);

        topicRepository.saveAll(topicList);

        Pageable pageable = Pageable.unpaged();
        List<TopicListDTO> topicFromDb = topicRepository.findByBoardIdCustom(topicTest.getBoardId(), pageable);

        // assert for verify that are not related topics
        Assertions.assertNotEquals(topicTest.getBoardId(), noRalatedTopicTest.getBoardId());

        Assertions.assertEquals(1, topicFromDb.size());

        Assertions.assertEquals(topicTest.getId(), topicFromDb.get(0).getId());

    }


}
