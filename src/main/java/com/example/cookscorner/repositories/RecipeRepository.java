package com.example.cookscorner.repositories;

import com.example.cookscorner.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
    List<Recipe> findAllByCategory(String category);

    @Query("SELECT r FROM Recipe r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(r.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Recipe> findByNameContaining(String searchTerm);
}
