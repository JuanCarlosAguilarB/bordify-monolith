package com.bordify.dtos;


import com.bordify.models.Color;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;


/**
 * Represents a data transfer object (DTO) for displaying a list of topics with their associated tasks.
 */
@Data
@AllArgsConstructor
public class TopicListDTO {

    /**
     * The unique identifier of the topic.
     */
    private UUID id;

    /**
     * The name of the topic.
     */
    private String name;

    /**
     * The color associated with the topic.
     */
    private Color color;

    /**
     * The list of tasks associated with the topic.
     */
    private List<TaskListDTO> tasks;

    /**
     * Constructs a TopicListDTO object with the given parameters.
     *
     * @param id The unique identifier of the topic.
     * @param name The name of the topic.
     * @param color The color associated with the topic.
     */
    public TopicListDTO(UUID id, String name, Color color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }
}