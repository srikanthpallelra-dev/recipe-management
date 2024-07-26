package com.srikanth.recipe_app.repository;

import com.srikanth.recipe_app.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    // Searches in ingredients with this name and returns true if it exits else false
    boolean existsByNameIgnoreCase(String name);
    // Fetches ingredient by name and returns ingredient
    Ingredient findByNameIgnoreCase(String name);
}
