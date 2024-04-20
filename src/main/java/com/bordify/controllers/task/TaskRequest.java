package com.bordify.controllers.task;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
//@Builder
public class TaskRequest {
    private String name;
    private String description;
    private UUID topicId;
    private List<String> taskItems = new ArrayList<>(); //assignees;
}
