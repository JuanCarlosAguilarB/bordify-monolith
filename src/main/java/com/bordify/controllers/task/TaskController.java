package com.bordify.controllers.task;

import com.bordify.dtos.TaskListDTO;
import com.bordify.exceptions.EntityNotFound;
import com.bordify.models.Task;
import com.bordify.models.TaskItem;
import com.bordify.repositories.TaskItemRepository;
import com.bordify.repositories.TaskRepository;
import com.bordify.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Controller for managing task operations.
 */
@RestController
@Tag(name = "Task")
public class TaskController {

    @Autowired
    TaskService taskServices;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskItemRepository taskItemRepository;

    /**
     * Creates a new task.
     *
     * @param createTaskRequest The request containing task details.
     * @param authentication    The authentication details of the user.
     * @return ResponseEntity containing the created task.
     */
    @PostMapping("/task/")
    @Operation(summary = "Create a task")
    public ResponseEntity<Task> createTask(
            @RequestBody TaskRequest createTaskRequest,
            Authentication authentication) {


        Task task = Task.builder()
                .name(createTaskRequest.getName())
                .description(createTaskRequest.getDescription())
                .topicId(createTaskRequest.getTopicId())
                .build();

        taskServices.createTask(task);

        if (createTaskRequest.getTaskItems() == null || createTaskRequest.getTaskItems().isEmpty()) {
            return ResponseEntity.ok(task);
        }

        List<TaskItem> taskItems = new ArrayList<>();

        for (String assignee : createTaskRequest.getTaskItems()) {
            TaskItem taskItem =  taskItemRepository.save(
                    TaskItem.builder()
                            .content(assignee)
                            .taskId(task.getId())
                            .isDone(false)
                            .build());
            taskItems.add(taskItem);
        }

        task.setTaskItems(taskItems);
        System.out.println("Task created: " + task);
        return ResponseEntity.status(HttpStatus.CREATED).body(task)   ;
    }



    /**
     * Deletes a task by its id.
     *
     * @param id The id of the task to delete.
     * @return ResponseEntity indicating the success of the operation.
     */
    @DeleteMapping("/task/{id}/")
    public ResponseEntity<?> deleteTopic(@PathVariable UUID id) {
        taskRepository.deleteById(id);
        return ResponseEntity.ok().body("Task deleted");
    }


    /**
     * Retrieves a paginated list of tasks.
     *
     * @param pageable Pagination information for the list.
     * @return ResponseEntity containing the paginated list of tasks.
     */
    @GetMapping("/task/")
    public ResponseEntity<?> getTask(Pageable pageable) {

        return ResponseEntity.ok(taskServices.listTasks(pageable));
    }

    /**
     * Retrieves a task by its id.
     *
     * @param id The id of the task to retrieve.
     * @return ResponseEntity containing the retrieved task.
     */
    @GetMapping("/task/{id}/")
    public ResponseEntity<?> getATask(@PathVariable UUID id) {
        TaskListDTO task = taskServices.getTaskForBoard(id).orElseThrow(() -> new EntityNotFound("Task not found"));
        return ResponseEntity.ok().body(task);
    }


    /**
     * Updates a task.
     * @param id          The id of the task to update.
     * @param taskRequest The request containing updated task details.
     * @return ResponseEntity indicating the success of the operation.
     */
    @PutMapping("/task/{id}/")
    @Transactional
    public ResponseEntity<?> handler(@PathVariable UUID id, @RequestBody TaskRequest taskRequest) {

        Task task = Task.builder()
                .id(id)
                .name(taskRequest.getName())
                .description(taskRequest.getDescription())
                .topicId(taskRequest.getTopicId())
                .build();

        taskServices.update(task);

        return ResponseEntity.ok().body(task);
    }

}
