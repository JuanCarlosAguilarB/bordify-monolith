package com.bordify.persistence;


import com.bordify.models.*;
import com.bordify.persistence.models.*;
import com.bordify.repositories.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class TaskRepositoryShould {


    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private TopicRepository topicRepository;

    @DisplayName("Should find task by id")
    @Test
    public void shouldFindTaskById() {

        User userTest = UserModelTestService.createValidUser();
        userRepository.save(userTest);

        Board boardTest = BoardModelTestService.createValidBoard(userTest);
        boardRepository.save(boardTest);

        Color colorTest = ColorModelTestService.createValidColor();
        colorRepository.save(colorTest);

        Topic topicTest = TopicModelTestService.createValidTopic(colorTest, boardTest);
        topicRepository.save(topicTest);


        Task taskTest = TaskModelTestService.createValidTask(topicTest);
        taskRepository.save(taskTest);

        Optional<Task> tasks = taskRepository.findById(taskTest.getId());

        assert tasks.isPresent();
        assert tasks.get().getId().equals(taskTest.getId());

    }

}
