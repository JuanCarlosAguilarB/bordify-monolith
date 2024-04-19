package com.bordify.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Represents a task item entity in the Bordify application. A task item belongs to a task
 * and contains content and a flag indicating whether it is done.
 * This class uses Lombok annotations to simplify the boilerplate code for getter, setter, and constructors.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskItem {

    /**
     * Unique identifier for the TaskItem. It is automatically generated and uses UUID as the ID type.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The content of the task item. This field cannot be null.
     */
    @Column(nullable = false)
    private String content;

    /**
     * The ID of the task associated with this task item. This field is used for database operations but is
     * not directly updatable or insertable through the TaskItem entity to prevent inconsistency.
     */
    @Column(nullable = false, name="task_id")
    private UUID taskId;

    /**
     * Flag indicating whether the task item is done or not.
     */
    private Boolean isDone;

    /**
     * The Task entity associated with this task item. Mapped using a many-to-one relationship where each
     * task item belongs to a single task. The @JoinColumn annotation specifies that this entity uses the
     * 'task_id' column in the TaskItem table to join to the Task table.
     */
    @JsonIgnore // Prevents serialization of the task field to avoid circular dependencies
    @ManyToOne
    @JoinColumn(name = "task_id", insertable = false, updatable = false)
    private Task task;

}
