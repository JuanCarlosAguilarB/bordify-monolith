package com.bordify.controllers.task_items;

import com.bordify.controllers.task.TaskItemsRequest;
import com.bordify.models.TaskItem;
import com.bordify.repositories.TaskItemRepository;
import com.bordify.services.TaskItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller class for managing task items.
 */
@Tag(name = "task items", description = "task items management operations")
@RestController
public class TaskItemsController {


    @Autowired
    private TaskItemService taskItemService;


    /**
     * Deletes a task item by its ID.
     *
     * @param id The ID of the task item to delete.
     * @return ResponseEntity with a message indicating successful deletion.
     */
    @DeleteMapping("/task-items/{id}/")
    public ResponseEntity<?> deleteById(@PathVariable UUID id) {

        taskItemService.delete(id);

        return ResponseEntity.ok("deleted");
    }

    /**
     * Updates a task item by its ID.
     *
     * @param id               The ID of the task item to update.
     * @param taskItemsRequest The request body containing updated task item information.
     * @return ResponseEntity with the updated task item.
     */
    @PatchMapping("/task-items/{id}/")
    public ResponseEntity<?> getById(
            @PathVariable UUID id,
            @RequestBody TaskItemsRequest taskItemsRequest) {

        TaskItem taskItem = TaskItem.builder()
                .id(id)
                .content(taskItemsRequest.getContent())
                .taskId(taskItemsRequest.getTaskId())
                .isDone(taskItemsRequest.getIsDone())
                .build();

        TaskItem taskItemUptated = taskItemService.update(taskItem);

        return ResponseEntity.ok(taskItemUptated);
    }

    /**
     * Creates a new task item.
     *
     * @param taskItemsRequest The request body containing information for the new task item.
     * @return ResponseEntity with the created task item.
     */
    @PostMapping("/task-items/")
    public ResponseEntity<?> getlistTaskitems(@RequestBody TaskItemsRequest taskItemsRequest) {

        TaskItem taskItem = TaskItem.builder()
                .content(taskItemsRequest.getContent())
                .taskId(taskItemsRequest.getTaskId())
                .isDone(false)
                .build();

        taskItemService.create(taskItem);

        return ResponseEntity.ok(taskItem);
    }




}
