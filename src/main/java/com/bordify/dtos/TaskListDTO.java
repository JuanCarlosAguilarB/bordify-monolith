package com.bordify.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.bordify.models.TaskItem;

import java.util.List;
import java.util.UUID;

/**
 * Represents a data transfer object (DTO) for displaying a list of tasks with their associated task items.
 */
@Data
public class TaskListDTO {

    /**
     * The unique identifier of the task.
     */
    private UUID id;

    /**
     * The unique identifier of the topic associated with the task.
     */
    private UUID topicId;

    /**
     * The name of the task.
     */
    private String name;

    /**
     * The description of the task.
     */
    private String description;

    /**
     * The list of task items associated with the task.
     */
    private List<TaskItem> taskItems;

    /**
     * Constructs a TaskListDTO object with the given parameters.
     *
     * @param id The unique identifier of the task.
     * @param name The name of the task.
     * @param description The description of the task.
     * @param topicId The unique identifier of the topic associated with the task.
     */
    public TaskListDTO(UUID id, String name, String description, UUID topicId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.topicId = topicId;
    }
}
