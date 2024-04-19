package com.bordify.repositories;

import com.bordify.models.Color;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for accessing and managing color entities in the database.
 */
public interface ColorRepository extends JpaRepository<Color, Integer> {
}
