package com.bordify.service;

import com.bordify.models.*;
import com.bordify.persistence.models.*;
import com.bordify.repositories.TaskItemRepository;
import com.bordify.repositories.TaskRepository;
import com.bordify.services.TaskItemService;
import com.bordify.shared.infrastucture.controlles.UnitTestBaseClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

import static org.mockito.Mockito.when;

public class TaskItemServiceShould extends UnitTestBaseClass {

    @InjectMocks
    TaskItemService service;

    @Mock
    TaskItemRepository repository;

    User user;
    Board board;
    Color color;
    Topic topic;
    Task task;

    @BeforeEach
    public void setup(){
        this.user = UserFactory.getRandomUser();
        this.board = BoardFactory.getRandomBoard(this.user);
        this.color = ColorFactory.getRandomColor();
        this.topic = TopicFactory.getRandomTopic(this.color,this.board);
        this.task = TaskFactory.getRandomTaks(this.topic);
    }

    @DisplayName("should delete a task item")
    @Test
    public void shouldDeleteATaskItem(){

        TaskItem taskItem = TaskItemFactory.getRandomTaskItem(task);

        service.delete(taskItem.getId());

        Mockito.verify(repository, Mockito.times(1)).deleteById(taskItem.getId());

    }

    @DisplayName("should update a task item")
    @Test
    public void shouldUpdateATaskItem(){

        TaskItem taskItem = TaskItemFactory.getRandomTaskItem(task);

        when(repository.existsById(taskItem.getId())).thenReturn(true);
        when(repository.findById(taskItem.getId())).thenReturn(Optional.of(taskItem));

        service.update(taskItem);

        Mockito.verify(repository, Mockito.times(1)).existsById(taskItem.getId());
        Mockito.verify(repository, Mockito.times(1)).findById(taskItem.getId());

    }

    @DisplayName("shouldn't update a non existed task item")
    @Test
    public void shouldntUpdateANonExistedTaskItem(){

        TaskItem taskItem = TaskItemFactory.getRandomTaskItem(task);

        when(repository.existsById(taskItem.getId())).thenReturn(false);

        Assertions.assertThrows(Exception.class,()->{
            service.update(taskItem);

        });

        Mockito.verify(repository, Mockito.times(1)).existsById(taskItem.getId());

    }


    @DisplayName("should create a task item")
    @Test
    public void shouldCreateATaskItem(){

        TaskItem taskItem = TaskItemFactory.getRandomTaskItem(task);

        when(repository.save(taskItem)).thenReturn(taskItem);

        service.create(taskItem);

        Mockito.verify(repository, Mockito.times(1)).save(taskItem);

    }

}
