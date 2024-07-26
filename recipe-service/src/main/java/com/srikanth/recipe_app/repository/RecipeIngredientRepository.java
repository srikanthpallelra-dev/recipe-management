package com.srikanth.recipe_app.repository;

import com.srikanth.recipe_app.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {
    // Searches in recipe-ingredient with recipe id and ingredient id and returns true if exits else false
    boolean existsByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
    // Fetches recipe-ingredient by recipe id and ingredient id and returns RecipeIngredient
    Optional<RecipeIngredient> findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
    // Delete recipe-ingredient with the given recipe id and ingredient id
    void deleteByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
}
