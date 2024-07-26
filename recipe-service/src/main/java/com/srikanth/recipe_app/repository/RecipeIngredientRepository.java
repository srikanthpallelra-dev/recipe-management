package com.srikanth.recipe_app.repository;

import com.srikanth.recipe_app.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Long> {
    boolean existsByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
    Optional<RecipeIngredient> findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
    void deleteByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
}
