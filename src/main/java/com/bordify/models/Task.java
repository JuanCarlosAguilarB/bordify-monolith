package com.bordify.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * Represents a task entity in the Bordify application. A task is associated with a topic
 * and can have multiple task items.
 * This class uses Lombok annotations to simplify the boilerplate code for getter, setter, and constructors.
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    /**
     * Unique identifier for the Task. It is automatically generated and uses UUID as the ID type.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Name of the task.
     */
    private String name;

    /**
     * Description of the task.
     */
    private String description;

    /**
     * The ID of the topic associated with this task. This field is used for database operations but is
     * not directly updatable or insertable through the Task entity to prevent inconsistency.
     */
    @Column(name = "topic_id")
    private UUID topicId;

    /**
     * The Topic entity associated with this task. Mapped using a many-to-one relationship where each task
     * belongs to a single topic. The @JoinColumn annotation specifies that this entity uses the 'topic_id'
     * column in the Task table to join to the Topic table.
     */
    @ManyToOne()
    @JoinColumn(name = "topic_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Topic topic;

    /**
     * List of task items associated with this task. Defined as a one-to-many relationship with cascade
     * type ALL, meaning all persistence operations (like save and delete) on a Task will cascade to its
     * task items.
     */
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<TaskItem> taskItems;

}
