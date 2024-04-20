package com.bordify.services;

import com.bordify.exceptions.EntityNotFound;
import com.bordify.models.TaskItem;
import com.bordify.repositories.TaskItemRepository;
import com.bordify.utils.UpdateFieldsOfClasses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


/**
 * Service class for managing task items.
 */
@Service
public class TaskItemService {

    @Autowired
    TaskItemRepository taskItemRepository;

    /**
     * Deletes a task item by its ID.
     *
     * @param taskItemId The ID of the task item to delete.
     */
    public void delete(UUID taskItemId) {
        taskItemRepository.deleteById(taskItemId);
    }

    /**
     * Updates a task item.
     *
     * @param taskItem The task item with updated information.
     * @return The updated task item.
     * @throws EntityNotFound if the task item does not exist.
     */
    public TaskItem update(TaskItem taskItem) {
        ensureTaskItemExist(taskItem);

        TaskItem taskItemToUpdate = taskItemRepository.findById(taskItem.getId()).get();

        UpdateFieldsOfClasses.updateFields(taskItemToUpdate, taskItem);

        taskItemRepository.save(taskItemToUpdate);

        TaskItem taskItemToResponse = TaskItem.builder()
                .id(taskItemToUpdate.getId())
                .content(taskItemToUpdate.getContent())
                .taskId(taskItemToUpdate.getTaskId())
                .isDone(taskItemToUpdate.getIsDone())
                .build();

        return taskItemToResponse;

    }

    /**
     * Ensures that the task item exists.
     *
     * @param taskItem The task item to check existence for.
     * @throws EntityNotFound if the task item does not exist.
     */
    public void ensureTaskItemExist(TaskItem taskItem) {
        if (!taskItemRepository.existsById(taskItem.getId())) {
            throw new EntityNotFound("TaskItem not found");
        }
    }


    /**
     * Creates a new task item.
     *
     * @param taskItem The task item to create.
     */
    public void create(TaskItem taskItem) {
        taskItemRepository.save(taskItem);
    }

}



