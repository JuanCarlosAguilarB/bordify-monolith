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

@Service
public class TaskService {


    @Autowired
    TaskRepository taskRepository;
    @Autowired
    private TaskItemRepository taskItemRepository;

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }


    public List<TaskListDTO> getTaskForBoard(UUID topicId, Pageable pageable) {

        List<TaskListDTO> tasks = taskRepository.findByTopicId(topicId, pageable);

        for (TaskListDTO task : tasks) {
            task.setTaskItems(taskItemRepository.findByTaskId(task.getId()));
        }

        return tasks;
    }

    public Page<TaskListDTO> getTaskOfTopic(UUID topicId, Pageable pageable) {

        List<TaskListDTO> tasks = taskRepository.findByTopicId(topicId, pageable);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), tasks.size());

        Page<TaskListDTO> taskPaginated = new PageImpl<>(tasks.subList(start, end), pageable, tasks.size());

        return taskPaginated;
    }

    public Page<Task> listTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }
    public List<Task> listTasksSliced(Pageable pageable) {
        return (List<Task>) taskRepository.findAll(pageable);
    }



}
