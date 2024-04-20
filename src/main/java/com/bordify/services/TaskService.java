package com.bordify.services;

import com.bordify.dtos.TaskListDTO;
import com.bordify.exceptions.EntityNotFound;
import com.bordify.models.Task;
import com.bordify.models.TaskItem;
import com.bordify.repositories.TaskItemRepository;
import com.bordify.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class for managing tasks within the application.
 * Provides methods to create tasks, retrieve tasks for specific topics, and list tasks with pagination.
 */
@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskItemRepository taskItemRepository;

    /**
     * Saves a new task entity in the database.
     *
     * @param task The task entity to be saved.
     * @return The saved task entity.
     */
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    /**
     * Retrieves a list of task DTOs for a given topic with populated task items.
     * Each task's related task items are fetched and set directly in the task DTO.
     *
     * @param topicId The UUID of the topic.
     * @param pageable The pagination information.
     * @return A list of {@link TaskListDTO} with task items populated.
     */
    public List<TaskListDTO> getTaskForBoard(UUID topicId, Pageable pageable) {
        List<TaskListDTO> tasks = taskRepository.findByTopicId(topicId, pageable);
        for (TaskListDTO task : tasks) {
            task.setTaskItems(taskItemRepository.findByTaskId(task.getId()));
        }
        return tasks;
    }

    /**
     * Retrieves a paginated view of task DTOs for a given topic.
     *
     * @param topicId The UUID of the topic.
     * @param pageable The pagination information.
     * @return A paginated list of {@link TaskListDTO}.
     */
    public Page<TaskListDTO> getTaskOfTopic(UUID topicId, Pageable pageable) {
        List<TaskListDTO> tasks = taskRepository.findByTopicId(topicId, pageable);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), tasks.size());
        Page<TaskListDTO> taskPaginated = new PageImpl<>(tasks.subList(start, end), pageable, tasks.size());
        return taskPaginated;
    }

    /**
     * Retrieves all tasks in a paginated format.
     *
     * @param pageable The pagination information.
     * @return A page of {@link Task} entities.
     */
    public Page<Task> listTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    /**
     * Retrieves all tasks as a list, respecting pagination but not returning it as a page structure.
     * Useful when pagination information is needed but not the paginated response structure.
     *
     * @param pageable The pagination information.
     * @return A list of {@link Task} entities.
     */
    public List<Task> listTasksSliced(Pageable pageable) {
        return (List<Task>) taskRepository.findAll(pageable);
    }

    /**
     * Retrieves a task with its associated task items.
     *
     * @param taskId The id of the task to retrieve.
     * @return Optional containing the task with its task items.
     */
    public Optional<TaskListDTO> getTaskForBoard(UUID taskId) {

        Optional<Task> task = taskRepository.findById(taskId);
        Optional<TaskListDTO> taskDTO = task.map(t -> {
            TaskListDTO taskListDTO = new TaskListDTO(
                    t.getId(),
                    t.getName(),
                    t.getDescription(),
                    t.getTopicId()

            );
            taskListDTO.setTaskItems(taskItemRepository.findByTaskId(t.getId()));
            return taskListDTO;
        });

        return taskDTO;
    }

    /**
     * Checks if a task with the given id exists.
     *
     * @param id The id of the task to check.
     * @throws EntityNotFound If the task does not exist.
     */
    public void ensureTaskExists(UUID id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFound("Task not found");
        }
    }

    /**
     * Updates a task, including its associated task items.
     *
     * @param task The task to update.
     * @return The updated task.
     */
    public Task update(Task task) {
        ensureTaskExists(task.getId());

//        Task taskupdated = taskRepository.save(task);

        List<UUID> taskItemsIds = new ArrayList<>();
        List<TaskItem> taskItems = new ArrayList<>();

        if (task.getTaskItems() == null || task.getTaskItems().isEmpty()) {
            taskItemRepository.deleteAllByTaskId(task.getId());
            return taskRepository.save(task);
        }


        for (TaskItem assignee : task.getTaskItems()) {
            TaskItem taskItem = taskItemRepository.findById(assignee.getId())
                    .orElseGet(() -> {
                        TaskItem newTaskItem = TaskItem.builder()
                                .id(assignee.getId())
                                .content(assignee.getContent())
                                .taskId(task.getId())
                                .isDone(false)
                                .build();
                        taskItemRepository.save(newTaskItem);

                        return newTaskItem;
                    });

            taskItem.setContent(assignee.getContent());
            taskItemsIds.add(taskItem.getId());
            taskItems.add(taskItem);
            taskItemRepository.save(taskItem);
        }

        taskItemRepository.deleteAllByIdNotIn(taskItemsIds);
        taskRepository.save(task);

        return task;

    }



}
