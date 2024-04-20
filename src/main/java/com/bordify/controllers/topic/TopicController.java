package com.bordify.controllers.topic;

import com.bordify.models.Topic;
import com.bordify.services.TaskService;
import com.bordify.services.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller class for managing topics.
 */
@RestController
@Tag(name = "Topic", description = "Topic management operations")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private TaskService taskService;

    /**
     * Retrieves tasks associated with a topic.
     *
     * @param topicId  The ID of the topic.
     * @param pageable Pagination information.
     * @return ResponseEntity with tasks of the specified topic.
     */
    @GetMapping("/topic/{topicId}/tasks/")
    public ResponseEntity<?> getTaskOfTopic(@PathVariable UUID topicId, Pageable pageable) {
        return ResponseEntity.ok(taskService.getTaskOfTopic(topicId, pageable));
    }

    @Operation(summary = "Create a new topic", description = "Creates a new topic", tags = { "Topic" })
    @PostMapping("/topics/")
    public ResponseEntity<?> createTopic(
            @RequestBody TopicRequest topicRequest,
            Authentication authentication){

        Topic topic = new Topic().builder()
                .name(topicRequest.getName())
                .boardId(topicRequest.getBoardId())
                .colorId(topicRequest.getColorId())
                .build();

                topicService.createTopic(topic);
        return ResponseEntity.status(HttpStatus.CREATED).body(topic);
    }

    /**
     * Partially updates a topic.
     *
     * @param id            The ID of the topic to update.
     * @param topicRequest  The request body containing partial information to update the topic.
     * @return ResponseEntity with the updated topic.
     */
    @PatchMapping("/topics/{id}/")
    public ResponseEntity<?> partialUpdate(
        @PathVariable UUID id,
         @RequestBody TopicRequest topicRequest) {

        Topic topic = new Topic().builder()
                .id(id)
                .name(topicRequest.getName())
                .colorId(topicRequest.getColorId())
                .boardId(topicRequest.getBoardId())
                .build();

        Topic topicUpdated = topicService.update(topic);

        return ResponseEntity.ok(topicUpdated);
    }

    /**
     * Updates a topic.
     *
     * @param id           The ID of the topic to update.
     * @param topicRequest The request body containing information to update the topic.
     * @return ResponseEntity with the updated topic.
     */
    @PutMapping("/topics/{id}/")
    public ResponseEntity update(@PathVariable UUID id, @RequestBody TopicRequest topicRequest) {

        Topic topic = new Topic().builder()
                .id(id)
                .name(topicRequest.getName())
                .colorId(topicRequest.getColorId())
                .boardId(topicRequest.getBoardId())
                .build();

        Topic topicUpdated = topicService.update(topic);

        return ResponseEntity.ok(topicUpdated);
    }

    /**
     * Deletes a topic by its ID.
     *
     * @param id The ID of the topic to delete.
     */
    @DeleteMapping("/topics/{id}/")
    public void deleteTopic(@PathVariable UUID id) {
        topicService.deleteTopic(id);
    }

}
