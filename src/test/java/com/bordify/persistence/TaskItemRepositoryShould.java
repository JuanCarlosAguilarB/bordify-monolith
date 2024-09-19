package com.bordify.persistence;

import com.bordify.models.*;
import com.bordify.persistence.models.*;
import com.bordify.repositories.*;
import com.bordify.shared.infrastucture.controlles.IntegrationSqlDbTestBaseClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bordify.utils.GeneratorValuesRandom.generateRandomValue;

public class TaskItemRepositoryShould extends IntegrationSqlDbTestBaseClass {


    private TopicFactory topicFactory;
    private UserFactory userFactory;
    private BoardFactory boardFactory;
    private ColorFactory colorFactory;
    private TaskFactory taskFactory;
    private TaskItemRepository taskItemRepository;

    private Task taskTest;
    private TaskItem taskItem;
    private Topic topicTest;

    @Autowired
    public TaskItemRepositoryShould(TaskItemRepository taskItemRepository, TaskRepository taskRepository, UserRepository userRepository, BoardRepository boardRepository, TopicRepository topicRepository, ColorRepository colorRepository){
        this.userFactory = new UserFactory(userRepository);
        this.colorFactory = new ColorFactory(colorRepository);
        this.boardFactory = new BoardFactory(boardRepository);
        this.topicFactory = new TopicFactory(topicRepository);
        this.taskFactory = new TaskFactory(taskRepository);
        this.taskItemRepository = taskItemRepository;
    }

    @BeforeEach
    public void setup(){

        User userTest = userFactory.getRandomUserPersisted();
        Board boardTest = boardFactory.getRandomBoardPersisted(userTest);
        Color colorTest = colorFactory.getRandomColorPersisted();
        topicTest = topicFactory.getRandomTopicPersisted(colorTest, boardTest);
        taskTest = taskFactory.getRandomTaskPersisted(topicTest);
        taskItem = TaskItemFactory.getRandomTaskItem(taskTest);

    }

    @DisplayName("should find a list of taskItem by task")
    @Test
    public void shouldFindAListByTaskId(){

        int amountElements = generateRandomValue(3, 15);
        List<TaskItem> taskItemList = TaskItemFactory.getRandomListOfTaskItem(amountElements, taskTest);
        taskItemRepository.saveAllAndFlush(taskItemList);

        List<TaskItem> taskItemResult = taskItemRepository.findByTaskId(taskTest.getId());

        Assertions.assertEquals(amountElements,taskItemList.size());
        Assertions.assertEquals(amountElements,taskItemResult.size());

//        taskItemResult.forEach(taskItem ->{
//            Assertions.assertTrue(taskItemList.contains(taskItem));
//        });

        List<UUID> idTaskItemList = new ArrayList<>();
        List<UUID> idTaskItemResult = new ArrayList<>();

//        // we need to compare ids, because thats aren't same objects
        taskItemList.forEach(task -> {
            idTaskItemList.add(task.getId());
        });

        taskItemResult.forEach(task -> {
            idTaskItemResult.add(task.getId());
        });

        // verify ids of task getting of db must be in the task saved
        idTaskItemResult.forEach(idTask -> {
            Assertions.assertTrue(idTaskItemList.contains(idTask));
        });

    }


    @DisplayName("should only find a list of taskItem by task")
    @Test
    public void shouldOnlyFindAListByTaskId(){

        int amountElements = generateRandomValue(3, 15);

        Task taskNoRelated = taskFactory.getRandomTaskPersisted(topicTest);
        List<TaskItem> taskItemListNoRelated  = TaskItemFactory.getRandomListOfTaskItem(amountElements, taskNoRelated);
        taskItemRepository.saveAll(taskItemListNoRelated);

        List<TaskItem> taskItemList = TaskItemFactory.getRandomListOfTaskItem(amountElements, taskTest);
        taskItemRepository.saveAllAndFlush(taskItemList);

        List<TaskItem> taskItemResult = taskItemRepository.findByTaskId(taskTest.getId());

        Assertions.assertEquals(amountElements,taskItemList.size());
        Assertions.assertEquals(amountElements,taskItemResult.size());

//        taskItemResult.forEach(taskItem ->{
//            Assertions.assertTrue(taskItemList.contains(taskItem));
//        });

        List<UUID> idTaskItemList = new ArrayList<>();
        List<UUID> idTaskItemResult = new ArrayList<>();

//        // we need to compare ids, because thats aren't same objects
        taskItemList.forEach(task -> {
            idTaskItemList.add(task.getId());
        });

        taskItemResult.forEach(task -> {
            idTaskItemResult.add(task.getId());
        });

        // verify ids of task getting of db must be in the task saved
        idTaskItemResult.forEach(idTask -> {
            Assertions.assertTrue(idTaskItemList.contains(idTask));
        });

    }

    @DisplayName("should delete taskItems than not found in a list of UUID")
    @Test
    public void shouldDeleteTaskItemsThanNotFoundInAListOfIds(){

        int elementsToCreate = generateRandomValue(3, 15);
        int elementsToPreserved = generateRandomValue(2, 14);

        List<TaskItem> taskItemList = TaskItemFactory.getRandomListOfTaskItem(elementsToCreate, taskTest);
        taskItemRepository.saveAllAndFlush(taskItemList);

        // this method was wrong, bc that method delete all task, but i need to delete taskItems
        // that it not found in [uuid] but has same taskId, so that's mean, delete taskItems relates with task

        List<UUID> idsToPreserved= new ArrayList<>();
        for ( int i=0; i<elementsToPreserved;i++){
            idsToPreserved.add(taskItemList.get(i).getId());
        }

        taskItemRepository.deleteAllByIdNotIn(idsToPreserved);
        taskItemRepository.flush();

        for ( int i=0; i<elementsToPreserved;i++){
            Assertions.assertTrue(
                    taskItemRepository.existsById(idsToPreserved.get(i))
            );
        }


    }

    // ### Todo: FIX that
    @DisplayName("should only delete taskItems than not found in a list of UUID and are of same task")
    @Test
    public void shouldOnlyDeleteTaskItemsThanNotFoundInAListOfIds(){

        int elementsToCreate = generateRandomValue(3, 15);
        int elementsToPreserved = generateRandomValue(2, 14);

        Task taskNoRelated = taskFactory.getRandomTaskPersisted(topicTest);
        List<TaskItem> taskItemListNoRelated  = TaskItemFactory.getRandomListOfTaskItem(elementsToCreate, taskNoRelated);
        taskItemRepository.saveAllAndFlush(taskItemListNoRelated);

        List<TaskItem> taskItemList = TaskItemFactory.getRandomListOfTaskItem(elementsToCreate, taskTest);
        taskItemRepository.saveAllAndFlush(taskItemList);

        // this method was wrong, bc that method delete all task, but i need to delete taskItems
        // that it not found in [uuid] but has same taskId, so that's mean, delete taskItems relates with task

        List<UUID> idsToPreserved= new ArrayList<>();
        for ( int i=0; i<elementsToPreserved;i++){
            idsToPreserved.add(taskItemList.get(i).getId());
        }


        taskItemRepository.deleteAllByIdNotIn(idsToPreserved);
        taskItemRepository.flush();

        for ( int i=0; i<elementsToPreserved;i++){
            Assertions.assertTrue(
                    taskItemRepository.existsById(idsToPreserved.get(i))
            );
        }
        System.out.println(taskItemListNoRelated);
        // Must exist another TaskItems
        for ( int i=0; i<elementsToCreate;i++){
            System.out.println(i);
            System.out.println(taskItemListNoRelated.get(0).getId());
            Assertions.assertTrue(
                    taskItemRepository.existsById(

                            taskItemListNoRelated.get(0).getId()
                    )
            );
        }

    }
}
