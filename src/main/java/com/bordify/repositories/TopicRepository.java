package com.bordify.repositories;

import com.bordify.models.Topic;
import com.bordify.dtos.TopicListDTO;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for accessing and managing topic entities in the database.
 */
public interface TopicRepository extends JpaRepository<Topic, UUID> {

    /**
     * Retrieves a list of topic DTOs by board ID with custom projection and pagination.
     *
     * @param boardId The ID of the board.
     * @param pageable The pagination information.
     * @return A list of topic DTOs associated with the specified board ID.
     */
    @Query("SELECT new com.bordify.dtos.TopicListDTO(t.id, t.name, c) FROM Topic t LEFT JOIN t.color c WHERE t.boardId = :boardId")
    List<TopicListDTO> findByBoardIdCustom(UUID boardId, Pageable pageable);
}