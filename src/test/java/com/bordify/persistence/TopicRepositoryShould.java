package com.bordify.persistence;

import com.bordify.dtos.TopicListDTO;
import com.bordify.models.Board;
import com.bordify.models.Color;
import com.bordify.models.Topic;
import com.bordify.models.User;
import com.bordify.persistence.models.BoardFactory;
import com.bordify.persistence.models.ColorFactory;
import com.bordify.persistence.models.TopicFactory;
import com.bordify.persistence.models.UserFactory;
import com.bordify.repositories.*;
import com.bordify.shared.infrastucture.controlles.IntegrationTestBaseClass;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bordify.utils.GeneratorValuesRandom.generateRandomValue;

@Transactional
public class TopicRepositoryShould extends IntegrationTestBaseClass {

    private TopicRepository topicRepository;
    private TopicFactory topicFactory;
    private UserFactory userFactory;
    private ColorFactory colorFactory;
    private TaskRepository taskRepository;
    private BoardFactory boardFactory;

    private Color colorTest;
    private User userTest;
    private Board boardTest;

    @Autowired
    public TopicRepositoryShould(BoardRepository boardRepository, TaskRepository taskRepository, UserRepository userRepository, TopicRepository topicRepository, ColorRepository colorRepository){
        this.userFactory = new UserFactory(userRepository);
        this.colorFactory = new ColorFactory(colorRepository);
        this.topicFactory = new TopicFactory(topicRepository);
        this.boardFactory = new BoardFactory(boardRepository);
        this.taskRepository = taskRepository;
        this.topicRepository = topicRepository;

    }

    @BeforeEach
    public void setup(){
        userTest = userFactory.getRandomUserPersisted();
        colorTest = colorFactory.getRandomColorPersisted();
        boardTest = boardFactory.getRandomBoardPersisted(userTest);

    }


    @DisplayName("Should find one topic by boardId")
    @Test
    public void shouldFindOneTopicByBoardId() {

        int amountTopics = 1;

        Topic topicTest = topicFactory.getRandomTopic(colorTest, boardTest);
        topicRepository.saveAll(List.of(topicTest));
        topicRepository.flush();

        Pageable pageable = Pageable.unpaged();
        List<TopicListDTO> topicsFound = topicRepository.findByBoardIdCustom(topicTest.getBoardId(), pageable);

        // assert for verify that are not related topics
        Assertions.assertEquals(topicsFound.size(), amountTopics);
        Assertions.assertEquals(topicsFound.get(0).getId(), topicTest.getId());


    }

    @DisplayName("Should find only one topic by boardId")
    @Test
    public void shouldFindOnlyOneTopicByBoardId() {

        int amountTopics = 1;

        // create others boards not related with board of test
        Board  noRalatedBoardTest = boardFactory.getRandomBoardPersisted(userTest);

        Topic topicTest = topicFactory.getRandomTopic(colorTest, boardTest);
        Topic noRalatedTopicTest = topicFactory.getRandomTopic(colorTest, noRalatedBoardTest);
        topicRepository.saveAll(List.of(topicTest, noRalatedTopicTest));

        Pageable pageable = Pageable.unpaged();
        List<TopicListDTO> topicFromDb = topicRepository.findByBoardIdCustom(topicTest.getBoardId(), pageable);

        // assert for verify that are not related topics
        Assertions.assertNotEquals(topicTest.getBoardId(), noRalatedTopicTest.getBoardId());
        Assertions.assertEquals(amountTopics, topicFromDb.size());
        Assertions.assertEquals(topicTest.getId(), topicFromDb.get(0).getId());

    }

    @DisplayName("Should find a list of topics by boardId")
    @Test
    public void shouldFindListOfTopicsByBoardId() {


        int amountTopics = generateRandomValue(3, 15);

        List<Topic> topicsTest = topicFactory.getRandomListOfTopics(amountTopics, colorTest, boardTest);
        topicRepository.saveAll(topicsTest);

        Pageable pageable = Pageable.unpaged();
        List<TopicListDTO> topicsList = topicRepository.findByBoardIdCustom(boardTest.getId(), pageable);


        Assertions.assertEquals(amountTopics, topicsList.size());
        Assertions.assertEquals(amountTopics, topicsTest.size());

        List<UUID> idTopisTest = new ArrayList<>();
        List<UUID> idTopisTestFounded = new ArrayList<>();

        // we save topics and receive of db topicsDtos
        topicsList.forEach(topic -> {
            idTopisTestFounded.add(topic.getId());
        });

        topicsTest.forEach(topic -> {
            idTopisTest.add(topic.getId());
        });

        // verify topics
        idTopisTest.forEach(idTopic -> {
            Assertions.assertTrue(idTopisTest.contains(idTopic));
        });

    }

    @DisplayName("Should find only a list of topics of board")
    @Test
    public void shouldFindOnlyListOfTopicsOfBoard() {

        int amountTopics = generateRandomValue(3, 15);

        Board  noRalatedBoardTest = boardFactory.getRandomBoardPersisted(userTest);
        List<Topic> topicsNoRealated = topicFactory.getRandomListOfTopics(amountTopics, colorTest, noRalatedBoardTest);
        topicRepository.saveAll(topicsNoRealated);

        List<Topic> topicsTest = topicFactory.getRandomListOfTopics(amountTopics, colorTest, boardTest);
        topicRepository.saveAll(topicsTest);

        topicRepository.flush();


        Pageable pageable = Pageable.unpaged();
        List<TopicListDTO> topicsList = topicRepository.findByBoardIdCustom(boardTest.getId(), pageable);

        Assertions.assertEquals(amountTopics, topicsList.size());
        Assertions.assertEquals(amountTopics, topicsTest.size());


        List<UUID> idTopisTest = new ArrayList<>();
        List<UUID> idTopisTestFounded = new ArrayList<>();

        // we save topics and receive of db topicsDtos
        topicsList.forEach(topic -> {
            idTopisTestFounded.add(topic.getId());
        });

        topicsTest.forEach(topic -> {
            idTopisTest.add(topic.getId());
        });

        // verify topics
        idTopisTest.forEach(idTopic -> {
            Assertions.assertTrue(idTopisTest.contains(idTopic));
        });

    }

    @DisplayName("Should find paginated a list of topics of board")
    @Test
    public void shouldFindPaginatedListOfTopicsOfBoard() {

        int amountTopics = generateRandomValue(3, 15);

        List<Topic> topicsTest = topicFactory.getRandomListOfTopics(amountTopics, colorTest, boardTest);
        topicRepository.saveAll(topicsTest);
        topicRepository.flush();

        int pageSize = generateRandomValue(3, amountTopics);
        int page = 0;
        Pageable pageable = Pageable.ofSize(pageSize);
        List<TopicListDTO> topicsList = topicRepository.findByBoardIdCustom(boardTest.getId(), pageable);

        Assertions.assertEquals(amountTopics, topicsTest.size());
        // topics from db are paginated
        Assertions.assertEquals(Math.min(amountTopics, pageSize), topicsList.size());

        List<UUID> idTopisTest = new ArrayList<>();
        List<UUID> idTopisTestFounded = new ArrayList<>();

        // we save topics and receive of db topicsDtos
        topicsList.forEach(topic -> {
            idTopisTestFounded.add(topic.getId());
        });

        topicsTest.forEach(topic -> {
            idTopisTest.add(topic.getId());
        });

        // verify topics
        idTopisTest.forEach(idTopic -> {
            Assertions.assertTrue(idTopisTest.contains(idTopic));
        });

    }


}
