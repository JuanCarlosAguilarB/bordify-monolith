package com.bordify.persistence.models;

import com.bordify.models.Color;
import com.bordify.models.Task;
import com.bordify.models.Topic;
import com.bordify.models.User;

import java.time.LocalTime;
import java.util.UUID;

public class TaskModelTestService {

    public static Task createValidTask() {

        Topic topic = TopicModelTestService.createValidTopic();

        Task task = Task.builder()
                .id(UUID.randomUUID())
                .name("Test Task")
                .description("This is a test task.")
                .topic(topic)
                .topicId(topic.getId())
                .build();

        return task;
    }


}
