package com.bordify.service;

import com.bordify.dtos.TaskListDTO;
import com.bordify.exceptions.EntityNotFound;
import com.bordify.models.*;
import com.bordify.persistence.models.*;
import com.bordify.repositories.TaskItemRepository;
import com.bordify.repositories.TaskRepository;
import com.bordify.services.TaskService;
import com.bordify.shared.infrastucture.controlles.UnitTestBaseClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

public class TaskServiceShould extends UnitTestBaseClass {

    @InjectMocks
    TaskService service;

    @Mock
    TaskRepository repository;

    @Mock
    TaskItemRepository taskItemRepository;

    User user;
    Board board;
    Color color;
    Topic topic;

    @BeforeEach
    public void setup(){
        this.user = UserFactory.getRandomUser();
        this.board = BoardFactory.getRandomBoard(this.user);
        this.color = ColorFactory.getRandomColor();
        this.topic = TopicFactory.getRandomTopic(this.color,this.board);
    }

    @DisplayName("should create a task")
    @Test
    public void shouldCrateAtask(){
        Task task = TaskFactory.getRandomTaks(topic);

        when(repository.save(task)).thenReturn(task);

        service.createTask(task);

        Mockito.verify(repository,Mockito.times(1)).save(task);

    }

    @DisplayName("should retrieve a list of task of board")
    @Test
    public void shouldRetrieveAListOfTaskOfBoard(){

        List<TaskListDTO> tasks = new ArrayList<>();
        Pageable pageable = Pageable.unpaged();

        when(repository.findByTopicId(topic.getId(),pageable)).thenReturn(tasks);

        service.getTaskForBoard(topic.getId(),pageable);

        Mockito.verify(repository,Mockito.times(1)).findByTopicId(topic.getId(),pageable);

    }

    @DisplayName("should retrieve a list of task of board with task items")
    @Test
    public void shouldRetrieveAListOfTaskOfBoardWithTaskItems(){

        List<TaskListDTO> tasks = Stream.of(TaskFactory.getRandomTaks(topic))
                .map(task -> TaskListDTO.builder()
                        .id(task.getId())
                        .name(task.getName())
                        .description(task.getDescription())
                        .topicId(task.getTopicId())
                        .taskItems(taskItemRepository.findByTaskId(task.getId()))
                        .build())
                .collect(Collectors.toList());

        System.out.println("tasks:");
        System.out.println(tasks);
        Pageable pageable = Pageable.unpaged();

        when(repository.findByTopicId(topic.getId(),pageable)).thenReturn(tasks);

        service.getTaskForBoard(topic.getId(),pageable);

        Mockito.verify(repository,Mockito.times(1)).findByTopicId(topic.getId(),pageable);
//        Mockito.verify(taskItemRepository,Mockito.times(1)).findByTaskId(Mockito.any());

    }

    @DisplayName("should retrieve a list of task of topic")
    @Test
    public void shouldRetrieveAListOfTaskOfTopic(){
        Task task = TaskFactory.getRandomTaks(topic);

        List<TaskListDTO> tasks = new ArrayList<>(); // create a list of dtos for pass test
        Pageable pageable = Pageable.ofSize(10);

        when(repository.findByTopicId(topic.getId(),pageable)).thenReturn(tasks);

        service.getTaskOfTopic(topic.getId(),pageable);

        Mockito.verify(repository,Mockito.times(1)).findByTopicId(topic.getId(),pageable);

    }

    @DisplayName("should retrieve a list of task of board by TaskId")
    @Test
    public void shouldRetrieveAListOfTaskOfBoardByTaskId(){

        Task task = TaskFactory.getRandomTaks(topic);
        TaskItem taskItem = TaskItemFactory.getRandomTaskItem(task);

        when(repository.findById(task.getId())).thenReturn(Optional.of(task));
        when(taskItemRepository.findByTaskId(task.getId())).thenReturn(List.of(taskItem));

        service.getTaskForBoard(task.getId());

        Mockito.verify(repository,Mockito.times(1)).findById(task.getId());
        Mockito.verify(taskItemRepository,Mockito.times(1)).findByTaskId(task.getId());

    }

    @DisplayName("should retrieve a list of task of board by TaskId without task items")
    @Test
    public void shouldRetrieveAListOfTaskOfBoardByTaskIdWithoutTaskItems(){

        Task task = TaskFactory.getRandomTaks(topic);

        when(repository.findById(task.getId())).thenReturn(Optional.of(task));
        when(taskItemRepository.findByTaskId(task.getId())).thenReturn(null);

        service.getTaskForBoard(task.getId());

        Mockito.verify(repository,Mockito.times(1)).findById(task.getId());
        Mockito.verify(taskItemRepository,Mockito.times(1)).findByTaskId(task.getId());

    }


//    @DisplayName("should update a task")
//    @Test
//    public void shouldUpdateATask() {
//
//        Task task = TaskFactory.getRandomTaks(topic);
//
//        when(repository.existsById(Mockito.any())).thenReturn(true);
//        when(repository.findById(topic.getId())).thenReturn(Optional.of(task));
//
//        service.update(task);
//
//        Mockito.verify(repository,Mockito.times(1)).existsById(topic.getId());
//        Mockito.verify(repository,Mockito.times(1)).findById(topic.getId());
//        Mockito.verify(repository,Mockito.times(1)).save(Mockito.any());
//    }



    @Test
    @DisplayName("Should update task without task items")
    public void shouldUpdateTaskWithoutTaskItems() {
        Task task = TaskFactory.getRandomTaks(topic);

        when(repository.existsById(task.getId())).thenReturn(true);
//        when(repository.findById(task.getId())).thenReturn(Optional.of(task));
//        when(taskItemRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());

        service.update(task);

        Mockito.verify(taskItemRepository, Mockito.times(1)).deleteAllByTaskId(Mockito.any(UUID.class));
        Mockito.verify(taskItemRepository, Mockito.never()).save(Mockito.any(TaskItem.class));
        Mockito.verify(repository, Mockito.times(1)).save(task);

    }


    @Test
    @DisplayName("Should update task and save new task items")
    public void shouldUpdateTaskWithNewTaskItems() {

        Task task = TaskFactory.getRandomTaks(topic);

        when(repository.existsById(task.getId())).thenReturn(true);
//        when(repository.findById(task.getId())).thenReturn(Optional.of(task));

        List<TaskItem> taskItems = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TaskItem taskItem = TaskItemFactory.getRandomTaskItem(task);
            taskItems.add(taskItem);
            when(taskItemRepository.findById(taskItem.getId())).thenReturn(Optional.of(taskItem));
        }

        task.setTaskItems(taskItems);

        Task updatedTask = service.update(task);

        Mockito.verify(repository, Mockito.times(1)).save(task);

        for (TaskItem taskItem : taskItems) {
            Mockito.verify(taskItemRepository, Mockito.times(1)).save(taskItem);
        }

        Mockito.verify(taskItemRepository, Mockito.times(1))
                .deleteAllByIdNotInAndTaskId(Mockito.anyList(), Mockito.eq(task.getId()));

        Assertions.assertNotNull(updatedTask);
        Assertions.assertEquals(taskItems.size(), updatedTask.getTaskItems().size());
    }


    @Test
    @DisplayName("Should throw exception if task does not exist")
    public void shouldThrowExceptionIfTaskDoesNotExist() {

        Task task = TaskFactory.getRandomTaks(topic);

        when(repository.existsById(task.getId())).thenReturn(false);

        Assertions.assertThrows(EntityNotFound.class, () -> service.update(task));

        Mockito.verify(repository, Mockito.never()).save(Mockito.any(Task.class));
        Mockito.verify(taskItemRepository, Mockito.never()).save(Mockito.any(TaskItem.class));
    }
}
