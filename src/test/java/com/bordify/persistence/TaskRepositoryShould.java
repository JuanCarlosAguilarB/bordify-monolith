package com.bordify.persistence;


import com.bordify.dtos.TaskListDTO;
import com.bordify.models.*;
import com.bordify.persistence.models.*;
import com.bordify.repositories.*;
import com.bordify.shared.infrastucture.controlles.IntegrationTestBaseClass;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.bordify.utils.GeneratorValuesRandom.generateRandomValue;

@Transactional
public class TaskRepositoryShould  extends IntegrationTestBaseClass {


    private TopicFactory topicFactory;
    private UserFactory userFactory;
    private BoardFactory boardFactory;
    private ColorFactory colorFactory;
    private TaskRepository taskRepository;

    private Topic topicTest;
    private Task taskTest;

    @Autowired
    public TaskRepositoryShould(TaskRepository taskRepository, UserRepository userRepository, BoardRepository boardRepository, TopicRepository topicRepository, ColorRepository colorRepository){
        this.userFactory = new UserFactory(userRepository);
        this.colorFactory = new ColorFactory(colorRepository);
        this.boardFactory = new BoardFactory(boardRepository);
        this.topicFactory = new TopicFactory(topicRepository);
        this.taskRepository = taskRepository;
    }


    @BeforeEach
    public void setup(){

        User userTest = userFactory.getRandomUserPersisted();
        Board boardTest = boardFactory.getRandomBoardPersisted(userTest);
        Color colorTest = colorFactory.getRandomColorPersisted();
        topicTest = topicFactory.getRandomTopicPersisted(colorTest, boardTest);

        taskTest = TaskFactory.getRandomTaks(topicTest);

    }


    @DisplayName("Should find task by id")
    @Test
    public void shouldFindTaskById() {

        taskRepository.save(taskTest);

        Optional<Task> tasks = taskRepository.findById(taskTest.getId());

        assert tasks.isPresent();
        assert tasks.get().getId().equals(taskTest.getId());

    }

    @DisplayName("Should find all tasks of topic")
    @Test
    public void shouldFindAllTasksOfTopic() {

        int taskAmount = generateRandomValue(5, 15);

        List<Task> listTaskTopic =  TaskFactory.getRandomListOfTask(topicTest, taskAmount);
        taskRepository.saveAll(listTaskTopic);

        Pageable pageable = Pageable.unpaged();
        List<TaskListDTO> tasks = taskRepository.findByTopicId(topicTest.getId(), pageable);

        assert !tasks.isEmpty();
        assert tasks.size() == taskAmount;
        assert tasks.stream().allMatch(task -> task.getTopicId().equals(topicTest.getId()));

    }



}
