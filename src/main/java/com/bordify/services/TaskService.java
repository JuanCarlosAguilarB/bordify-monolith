package com.bordify.services;

import com.bordify.dtos.TaskListDTO;
import com.bordify.models.Task;
import com.bordify.repositories.TaskItemRepository;
import com.bordify.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
