package com.bordify.persistence.models;

import com.bordify.models.Task;
import com.bordify.models.Topic;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bordify.shared.domain.FactoryValues.generateRandomAlphanumeric;
import static com.bordify.utils.GeneratorValuesRandom.generateRandomValue;

public class TaskFactory {


    public static Task getRandomTaks(Topic topic) {

        Task task = Task.builder()
                .id(UUID.randomUUID())
                .name(generateRandomAlphanumeric(generateRandomValue(1,50)))
                .description(generateRandomAlphanumeric(generateRandomValue(1,50)))
                .topic(topic)
                .topicId(topic.getId())
                .build();

        return task;
    }


    public static List<Task> getRandomListOfTask(Topic topic, int amountTask) {

        String generatedString = RandomStringUtils.randomAlphanumeric(10);

        List<Task> listTasks = new ArrayList<>();

        for (int i = 0; i < amountTask; i++) {

            String name = RandomStringUtils.randomAlphanumeric(10);
            String description = RandomStringUtils.randomAlphanumeric(10);

            listTasks.add(
                    Task.builder()
                            .id(UUID.randomUUID())
                            .name(name)
                            .description(description)
                            .topic(topic)
                            .topicId(topic.getId())
                            .build()
            );
        }
        return listTasks;
    }
}

