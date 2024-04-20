package com.bordify.controllers.topic;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TopicRequest {
    private String name;
    private String description;
    private Integer colorId;
    private UUID boardId;
}
