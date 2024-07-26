package com.srikanth.recipe_app.service;

import com.srikanth.recipe_app.dto.RecipeIngredientDTO;

public interface RecipeIngredientService {
    RecipeIngredientDTO addIngredientToRecipe(Long recipeId, String ingredientName);
    void removeIngredientFromRecipe(Long recipeId, String ingredientName);
}
