package com.bordify.services;

import com.bordify.dtos.TopicListDTO;
import com.bordify.exceptions.EntityNotFound;
import com.bordify.models.Topic;
import com.bordify.repositories.TopicRepository;
import com.bordify.utils.UpdateFieldsOfClasses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


/**
 * Service class for managing topics within the application.
 * Provides methods to create, update, delete topics and retrieve topics with their associated tasks.
 */
@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private TaskService taskService;

    /**
     * Updates an existing topic based on the provided topic object.
     * Ensures the topic exists before updating and returns the updated topic.
     *
     * @param topic The topic with updated fields.
     * @return The updated and saved topic entity.
     */
    public Topic update(Topic topic) {
        ensureTopicExist(topic);

        Topic topicToUpdate = topicRepository.findById(topic.getId()).orElseThrow(() ->
                new EntityNotFound("Topic not found for ID " + topic.getId()));
        UpdateFieldsOfClasses.updateFields(topicToUpdate, topic);
        topicRepository.save(topicToUpdate);

        return Topic.builder()
                .id(topicToUpdate.getId())
                .name(topicToUpdate.getName())
                .colorId(topicToUpdate.getColorId())
                .boardId(topicToUpdate.getBoardId())
                .build();
    }

    /**
     * Verifies if a topic exists in the database based on its ID.
     *
     * @param topic The topic to check.
     */
    public void ensureTopicExist(Topic topic) {
        if (!topicRepository.existsById(topic.getId())) {
            throw new EntityNotFound("Topic not found");
        }
    }

    /**
     * Retrieves a paginated list of topics for a specified board with their related tasks.
     *
     * @param boardId The UUID of the board.
     * @param pageable The pagination information.
     * @return A page of {@link TopicListDTO} each populated with related tasks.
     */
    public Page<TopicListDTO> getTopicsOfBoard(UUID boardId, Pageable pageable) {
        List<TopicListDTO> topics = topicRepository.findByBoardIdCustom(boardId, pageable);
        System.out.println("topics: " + topics);
        int pageNumber = 0;
        int pageSize = 5;
        Pageable pageableTask = PageRequest.of(pageNumber, pageSize);

        for (TopicListDTO topic : topics) {
            topic.setTasks(taskService.getTaskForBoard(topic.getId(), pageableTask));
        }

        return new PageImpl<>(topics, pageable, topics.size());
    }

    /**
     * Deletes a topic by its UUID.
     *
     * @param id The UUID of the topic to be deleted.
     */
    public void deleteTopic(UUID id) {
        topicRepository.deleteById(id);
    }

    /**
     * Saves a new topic entity to the database.
     *
     * @param topic The topic entity to be saved.
     */
    public void createTopic(Topic topic) {
        topicRepository.save(topic);
    }
}