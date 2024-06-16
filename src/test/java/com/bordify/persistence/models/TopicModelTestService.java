package com.bordify.persistence.models;

import com.bordify.models.Board;
import com.bordify.models.Color;
import com.bordify.models.Topic;

import java.util.UUID;

public class TopicModelTestService {

    public static Topic createValidTopic(Color color, Board  board) {

        Topic topic = Topic.builder()
                .id(UUID.randomUUID())
                .name("Test Topic")
                .color(color)
                .colorId(color.getId())
                .board(board)
                .boardId(board.getId())
                .build();

        return topic;
    }

}
