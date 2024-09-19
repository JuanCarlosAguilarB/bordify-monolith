package com.bordify.persistence.models;

import com.bordify.models.Task;
import com.bordify.models.TaskItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bordify.shared.domain.FactoryValues.generateRandomAlphanumeric;
import static com.bordify.utils.GeneratorValuesRandom.generateRandomValue;

public class TaskItemFactory {

    public static TaskItem getRandomTaskItem(Task task){

        return TaskItem.builder()
                .id(UUID.randomUUID())
                .content(generateRandomAlphanumeric(generateRandomValue(5, 30)))
                .taskId(task.getId())
                .isDone(false)
                .build();

    }

    public static List<TaskItem> getRandomListOfTaskItem(int amountTaskItem, Task task){
        List<TaskItem> results = new ArrayList<>();
        for (int i=0; i<amountTaskItem; i++){
            results.add(getRandomTaskItem(task));
        }
        return results;
    }

}
