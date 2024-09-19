package com.bordify.persistence.models;

import com.bordify.models.Board;
import com.bordify.models.Color;
import com.bordify.models.Topic;
import com.bordify.repositories.TopicRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.bordify.shared.domain.FactoryValues.generateRandomAlphanumeric;
import static com.bordify.utils.GeneratorValuesRandom.generateRandomValue;

public class TopicFactory {

    private TopicRepository topicRepository;

    public static Topic getRandomTopic(Color color, Board  board) {

        Topic topic = Topic.builder()
                .id(UUID.randomUUID())
                .name(generateRandomAlphanumeric(generateRandomValue(1,50)))
                .color(color)
                .colorId(color.getId())
                .board(board)
                .boardId(board.getId())
                .build();

        return topic;
    }

    public List<Topic> getRandomListOfTopics(int amountTopics, Color color, Board  board){
        List<Topic> topics = new ArrayList<>();
        for (int i=0; i<amountTopics; i++){
            topics.add(getRandomTopic(color, board));
        }
        return topics;
    }

    public TopicFactory(TopicRepository topicRepository){
        this.topicRepository=topicRepository;
    }

    public Topic getRandomTopicPersisted(Color color, Board  board){
        Topic topic = getRandomTopic(color, board);
        topicRepository.save(topic);
        return topic;
    }
}
